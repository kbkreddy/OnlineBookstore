<script setup lang="ts">
import { inject, provide, ref, watch } from "vue";
import { BookItem, CategoryItem } from "@/types";
import CategoryNav from "@/components/CategoryNav.vue";
import CategoryBookList from "@/components/CategoryBookList.vue";

// See https://router.vuejs.org/guide/advanced/composition-api.html
// for using the composition API with the Vue router.

// Because we don't have access to *this* inside of setup,
// we cannot directly access this.$router or this.$route
// as we can in the Options API.
// Instead, we import and use the useRouter and useRoute functions.
// In this code, we need the category name, so we use route.
import { useRoute } from "vue-router";
const route = useRoute();
// bookList is a reactive object.
// In the Options API, we would use the data option to hold reactive data.
// In the Composition API, we declare a "ref"
let bookList = ref([] as BookItem[]);

// Inject the unchanging category list that was provided in the AppFetchCategories component
const categoryList: CategoryItem[] = inject("categoryList", []);
console.log("Name of first category: " + categoryList[0].name);

// We are declaring this function async because we are calling "fetch" inside of it.
async function fetchBooks(categoryName: string) {
  // We're using the find method (which takes a lambda expression)
  // to get the category associated with the dynamic "name" parameter.
  // If there is no such category, we just return the first category from the list.
  const port = location.port === "8081" ? "8080" : location.port;
  const url =
    location.protocol +
    "//" +
    location.hostname +
    ":" +
    port +
    process.env.BASE_URL +
    "api/categories/name/" +
    categoryName +
    "/books/";
  // const url =
  //   // The URL for json-server contains a query to just return the books with the specified category ID
  //   "http://localhost:8080/BharathBookstoreFetch/api/categories/name/" +
  //   categoryName +
  //   "/books/";
  // Here's where we fetch the data at the URL and turn it into JSON
  // We put the resulting JSON into the value property of the bookList ref
  bookList.value = await fetch(url).then((response) => response.json());
}

// The route object is a reactive object,
// so any of its properties can be watched.
// You should avoid watching the whole route object.
// In most scenarios, you should directly watch the param you are expecting to change.

// Note: immediate: true option ensure that fetchBooks is run on load
watch(
  () => route.params.name,
  (newName) => {
    // because parameters can potentially be repeatable,
    // TypeScript thinks newName can be of type string OR string[].
    // Therefore, use "as string" to force TS to treat newName as a string
    fetchBooks(newName as string);
  },
  // The immediate option ensures that fetchBooks will run when the component is created
  { immediate: true }
);
</script>

<style scoped>
.category-buttons {
  display: flex;
  flex-direction: row;
  text-align: center;
  background-color: var(--neutral-color);
  border-radius: 0ch;
}

.button.selected-category-button {
  background-color: var(--primary-color);
  width: 25%;
  border-radius: 0ch;
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: var(--neutral-color);
  border-radius: 0ch;
}

.button.unselected-category-button:hover,
.button.unselected-category-button:active {
  background-color: var(--primary-color);
}
#book-boxes {
  display: flex;
  flex-wrap: wrap;
  padding: 1em;
  gap: 1em;
  background-color: #d4c3c3;
}
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
</style>

<template>
  <div class="category-page" style="position: relative">
    <category-nav></category-nav>
    <section class="category-book-list">
      <category-book-list :bookList="bookList"></category-book-list>
    </section>
    <div style="position: absolute; bottom: 5px; right: 30px">
      <Button style="border: none; background-color: transparent">
        <i class="fa-solid fa-angle-left"></i> Page 1 of 20
        <i class="fa-solid fa-angle-right"></i
      ></Button>
    </div>
  </div>
</template>
