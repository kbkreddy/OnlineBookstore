<script setup lang="ts">
import { defineProps } from "vue";
import { BookItem } from "@/types";
import { useCartStore } from "@/stores/CartStore";

const cartStore = useCartStore();

const props = defineProps<{
  book: BookItem;
}>();
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.jpg`;
};

function addToCart(book: BookItem) {
  cartStore.addToCart(book);
}
</script>
<style scoped>
.book-box {
  display: flex;
  background-color: var(--card-background-color);
  padding: 1em;
  gap: 0.25em;
  zoom: 86.5%;
}
.book-title {
  font-weight: bold;
}

.book-author {
  font-style: italic;
}
.categorypage-text {
  font-size: 20px;
}
.categorypage-button {
  font-size: 15px;
}

.categorypage-text {
  font-size: 17px;
}

.blur-image:hover {
  -webkit-filter: blur(4px);
}
</style>

<template>
  <li class="book-box" style="position: relative">
    <div class="book-image" style="position: relative">
      <a href="#">
        <img
          :src="require('@/assets/myimages/books/' + bookImageFileName(book))"
          style="height: 187px"
          :alt="book.title"
          :class="book.isPublic === true ? 'blur-image' : ''"
        />
      </a>
      <button
        v-if="book.isPublic"
        style="position: absolute; bottom: 0; width: 100%; cursor: pointer"
      >
        SAMPLE
      </button>
    </div>
    <div style="margin-left: 10px; width: 120px">
      <div class="book-title categorypage-text">{{ book.title }}</div>
      <div class="book-author categorypage-text">{{ book.author }}</div>
      <div class="book-price categorypage-text">{{ book.price }}</div>
      <div
        style="
          display: flex;
          flex-direction: column;
          position: absolute;
          width: 40%;
          bottom: 16px;
        "
      >
        <button
          class="button categorypage-button"
          @click="addToCart(book)"
          style="margin-bottom: 4px"
        >
          Add to Cart
        </button>
        <button class="button categorypage-button">Buy Now</button>
      </div>
    </div>
  </li>
</template>
