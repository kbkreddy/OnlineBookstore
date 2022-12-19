package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private OrderDao orderDao;
	private LineItemDao lineItemDao;
	private CustomerDao customerDao;

	Random random = new Random();

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	private Date getDate(String monthString, String yearString) {
		DateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
		Date inputDate;
		try {
			inputDate = dateFormat.parse(monthString + "-" + yearString);
		} catch (Exception e) {
			throw new ApiException("date parsing failed", e);
		}
		return inputDate;
	}

	@Override
	public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}

	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection,customerOrderId,item.getBookId(),
						item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private int generateConfirmationNumber() {

		return random.nextInt(999999999);
	}

	private void validateCustomer(CustomerForm customerForm) {

		String name = customerForm.getName();
		String address = customerForm.getAddress();
		String phone = customerForm.getPhone();
		String email = customerForm.getEmail();
		String ccNumber = customerForm.getCcNumber();

		//Validation for name field
		if (name == null || name.length() > 45 || name.length() < 4) {
			throw new ApiException.InvalidParameter("Invalid Name field");
		}

		//Validation for address field
		if (address == null || address.length() > 45 || address.length() < 4) {
			throw new ApiException.InvalidParameter("Invalid Address field");
		}

		//Validation for phone number field
		if (phone == null) {
			throw new ApiException.InvalidParameter("Invalid Phone number");
		}
		else{
			String phoneAfterTrim = phone.replaceAll("[()\\s-]+", "").trim();
			if(phoneAfterTrim.length() != 10){
				throw new ApiException.InvalidParameter("Invalid Phone number");
			}
		}

		//Validation for email field

		if(email == null ) {
			throw new ApiException.InvalidParameter("Invalid Email field");
		}
		else{
			String emailRegex = "^[\\w!#$%&'+/=?`{|}^-]+(?:\\.[\\w!#$%&'+/=?`{|}^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
			Pattern pattern = Pattern.compile(emailRegex);
			Matcher matcher = pattern.matcher(email);
			if(email.equals("") || !matcher.matches()){
				throw new ApiException.InvalidParameter("Invalid Email field");
			}
		}

		//Validation for cc field
		if (ccNumber == null ) {
			throw new ApiException.InvalidParameter("Invalid Credit Card Number");
		}
		else{
			String ccNumberAfterTrim = ccNumber.replaceAll("[\\s-]+", "").trim();
			if(ccNumberAfterTrim.length() > 16 || ccNumberAfterTrim.length() < 14){
				throw new ApiException.InvalidParameter("Invalid Credit Card Number");
			}
		}

		//Validation for date field
		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.InvalidParameter("Invalid Expiry date");

		}
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {

		if(ccExpiryMonth == null || ccExpiryYear == null || ccExpiryMonth.length() == 0 || ccExpiryYear.length() == 0) {
			return true;
		}
		int givenMonth = Integer.parseInt(ccExpiryMonth);
		int givenYear = Integer.parseInt(ccExpiryYear);

		if(!(givenMonth>0 && givenMonth <=12 && givenYear>0)) {
			return true;
		}

		YearMonth curr = YearMonth.now();
		YearMonth provided = YearMonth.of(Integer.parseInt(ccExpiryYear), Integer.parseInt(ccExpiryMonth));
		if(provided.isBefore(curr)){
			return true;
		}
		return false;

	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() == 0) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 1 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());
			//Matching book price with DB
			if(databaseBook.getPrice() != item.getBookForm().getPrice()) {
				throw new ApiException.InvalidParameter("Invalid Book price");
			}

			//Matching book category with DB
			if(databaseBook.getCategoryId() != item.getBookForm().getCategoryId()) {
				throw new ApiException.InvalidParameter("Invalid Book category");
			}
		});
	}

}