<script setup lang="ts">
// import { getBookImageUrl } from "@/utils";
import { ShoppingCart } from "@/models/ShoppingCart";
import { asDollarsAndCents } from "@/utils";

const initCart = new ShoppingCart();

import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";
import { BookItem, OrderDetails } from "@/types";

const orderDetailsStore = useOrderDetailsStore();
const orderDetails: OrderDetails = orderDetailsStore.orderDetails;

console.log(orderDetails);
// A helper function - optional to use
const bookAt = function (orderDetails: OrderDetails, index: number): BookItem {
  return orderDetails.books[index];
};
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.jpg`;
};

function getSubTotal() {
  let amount = 0;
  orderDetails.books.forEach((bookItem, index) => {
    amount += bookItem.price * orderDetails.lineItems[index].quantity;
  });
  return amount;
}
</script>

<style scoped>
table {
  border: 1px black solid;
  width: fit-content;
  margin-top: 1em;
  margin-bottom: 1em;
}

td {
  display: table-cell;
  padding: 0.5em 0.5em;
  background-color: white;
  vertical-align: middle;
}

tr:nth-child(even) > td {
  background-color: lightgray;
}

td:nth-child(1) {
  text-align: left;
}

td:nth-child(2) {
  text-align: center;
}

td:nth-child(3) {
  text-align: right;
}

.tdtop {
  background-color: #8a5203;
  color: white;
}
</style>

<template>
  <table style="margin-left: 3em">
    <tr>
      <td class="tdtop">Book Image</td>
      <td class="tdtop">Book Title</td>
      <td class="tdtop">Book Quantity</td>
      <td class="tdtop">Total Book Price</td>
    </tr>
    <tr v-for="(item, index) in orderDetails.lineItems" :key="index">
      <td>
        <img
          :src="
            require('@/assets/myimages/books/' +
              bookImageFileName(orderDetails.books[index]))
          "
          style="height: 127px"
          :alt="orderDetails.books[index].title"
          :class="
            orderDetails.books[index].isPublic === true ? 'blur-image' : ''
          "
        />
      </td>
      <td>
        {{ orderDetails.books[index].title }}
      </td>
      <td>{{ item.quantity }}</td>
      <td>
        {{ asDollarsAndCents(orderDetails.books[index].price * item.quantity) }}
      </td>
    </tr>
    <tr>
      <td></td>
      <td></td>
      <td>Sub Total</td>
      <td>{{ asDollarsAndCents(getSubTotal()) }}</td>
    </tr>
    <tr>
      <td></td>
      <td></td>
      <td>Surcharge</td>
      <td>{{ asDollarsAndCents(initCart.surcharge) }}</td>
    </tr>
    <tr>
      <td></td>
      <td></td>
      <td class="tdtop">Total</td>
      <td>{{ asDollarsAndCents(getSubTotal() + initCart.surcharge) }}</td>
    </tr>
  </table>
</template>
