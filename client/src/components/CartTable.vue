<script setup lang="ts">
import { useCartStore } from "@/stores/CartStore";
import { asDollarsAndCents } from "@/main";
const cartStore = useCartStore();
import { BookItem } from "@/types";
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.jpg`;
};
// TODO If necessary, replace the above function with the "bookImageFileName" function
//  TODO from your CategoryBookListItem component.

const updateCart = function (book: BookItem, quantity: number) {
  //cartStore.cart.update(book, quantity);
  cartStore.updateBookQuantity(book, quantity);
};
// TODO The above function calls through directly to the shopping cart, which means it does not update local storage.
// TODO Change the implementation so that it uses the appropriate action from the cart storage.
</script>

<style scoped>
.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 20em) repeat(3, max-content);
  row-gap: 1em;
  width: fit-content;
  margin: 0 auto;
  background-color: aliceblue;
}

ul {
  display: contents;
}

ul > li {
  display: contents;
}

.button-continue,
.button-continue:visited {
  display: inline-block;
  background: #8a5203;
  color: rgb(255, 255, 255);
  text-decoration: none;
  border-radius: 5px;
  padding: 0.5em 1em;
  cursor: pointer;
}

.button-continue:hover,
.button-continue:active {
  background: #cda1a1;
  color: rgb(16, 1, 1);
}

.table-heading {
  background-color: #333;
  color: white;
}

.table-heading > * {
  background-color: #333;
  padding: 0.5em;
}

.heading-book {
  grid-column: 1 / 3;
}

.heading-price {
  grid-column: 3 / 5;
  text-align: right;
}

.heading-subtotal {
  text-align: right;
  grid-column: -2 / -1;
}

.cart-book-image {
  padding: 0 1em;
}

.cart-book-image > * {
  margin-left: auto;
  margin-right: 0;
}

img {
  display: block;
  width: 100px;
  height: auto;
}
/* TODO The width above is for book images that are normally 200px wide. Change this (if necessary) */
/* TODO to accommodate your own book images. the rule of thumb is that the image here should be about */
/* TODO half the size of the book images as they appear on your category page. */

.cart-book-price {
  padding-left: 1em;
  text-align: right;
}

.cart-book-quantity {
  padding-left: 1em;
  padding-right: 1em;
}

.cart-book-subtotal {
  text-align: right;
  padding-left: 1em;
  padding-right: 1em;
}

/* Row separators in the table */

.line-sep {
  display: block;
  height: 2px;
  background-color: gray;
  grid-column: 1 / -1;
}

/* Increment/decrement buttons */

/* TODO Consider using icon buttons for your increment and decrement buttons. */
/* TODO Modify the CSS here to suit your own design. */
/* TODO In particular, you may or may not have custom properties */
/* TODO primary-color and primary-color-dark defined in your global CSS file. */
.icon-button {
  border: none;
  cursor: pointer;
}

.inc-button {
  font-size: 1.125rem;
  /*color: var(--primary-color);*/
  margin-right: 0.25em;
  margin-left: 0.4em;
}

.inc-button:hover {
  color: var(--primary-color-dark);
}

.dec-button {
  font-size: 1.125rem;
  /*color: #ccc;*/
  margin-right: 0.4em;
}

.dec-button:hover {
  color: #aaa;
}

select {
  background-color: var(--primary-color);
  color: white;
  border: 2px solid var(--primary-color-dark);
  border-radius: 3px;
}
</style>

<template>
  <div
    v-if="cartStore.cart.items.length !== 0"
    style="
      display: flex;
      flex-direction: row;
      width: auto;
      margin-bottom: 0.7em;
    "
  >
    <div
      v-if="
        cartStore.cart.items.reduce((Sum, item) => item.quantity + Sum, 0) !== 1
      "
      style="margin-left: 14em"
    >
      Your cart contains
      {{ cartStore.cart.items.reduce((Sum, item) => item.quantity + Sum, 0) }}
      Books
    </div>
    <div v-else style="margin-left: 14em">
      Your cart contains
      {{ cartStore.cart.items.reduce((Sum, item) => item.quantity + Sum, 0) }}
      Book
    </div>

    <div style="margin-left: 25em">
      <button
        style="background-color: #cda1a1"
        class="button"
        @click="cartStore.clearCart()"
      >
        Clear Cart
      </button>
    </div>
  </div>
  <div v-if="cartStore.cart.items.length !== 0" class="cart-table">
    <ul>
      <li class="table-heading">
        <div class="heading-book">Book</div>
        <div class="heading-price">Price / Quantity</div>
        <div class="heading-subtotal">Amount</div>
      </li>
      <template v-for="item in cartStore.cart.items" :key="item.book.bookId">
        <li>
          <div class="cart-book-image">
            <img
              :src="
                require('@/assets/myimages/books/' +
                  bookImageFileName(item.book))
              "
              :alt="item.book.title"
              style="height: 120px"
            />
          </div>
          <div class="cart-book-title">{{ item.book.title }}</div>
          <div class="cart-book-price">
            ${{ (item.book.price / 100).toFixed(2) }}
          </div>
          <div class="cart-book-quantity">
            <i
              @click="updateCart(item.book, item.quantity - 1)"
              class="dec-button fa-solid fa-minus-square"
              style="cursor: pointer"
            ></i>
            <span class="quantity">{{ item.quantity }}</span>
            <i
              @click="updateCart(item.book, item.quantity + 1)"
              class="inc-button fa-solid fa-plus-square"
            ></i>
          </div>
          <div class="cart-book-subtotal">
            {{ asDollarsAndCents(item.book.price * item.quantity) }}
          </div>
        </li>
        <li class="line-sep"></li>
      </template>
    </ul>
  </div>
  <div v-else>Your book cart is empty.</div>
  <div
    style="
      display: flex;
      flex-direction: column;
      width: auto;
      justify-content: center;
    "
  >
    <div style="margin-left: 52em" v-if="cartStore.cart.items.length !== 0">
      <p>
        SubTotal
        {{ asDollarsAndCents(cartStore.cart.subtotal) }}
      </p>
    </div>
    <div
      style="
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        margin-bottom: 1em;
        margin-top: 1em;
      "
    >
      <router-link :to="'/category/' + cartStore.selectedCategoryName"
        ><button class="button">Continue Shopping</button></router-link
      >
      <router-link to="/checkout"
        ><button class="button-continue" style="width: 400px">Checkout</button>
      </router-link>
    </div>
  </div>
</template>
