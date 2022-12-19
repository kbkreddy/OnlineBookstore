<script setup lang="ts">
import { provide } from "vue";
import { CategoryItem } from "@/types";
import AppHeader from "@/components/AppHeader.vue";
import AppFooter from "@/components/AppFooter.vue";
import { useRoute } from "vue-router";
const route = useRoute();
import { useCategoryStore } from "@/stores/CategoryStore";
const categoryStore = useCategoryStore();
categoryStore.fetchCategories();
// const port = location.port === "8081" ? "8080" : location.port;
// const url =
//   location.protocol +
//   "//" +
//   location.hostname +
//   ":" +
//   port +
//   process.env.BASE_URL +
//   "api/categories/";
// const categoryList: CategoryItem[] = await fetch(url).then((response) =>
//   response.json()
// );
// console.log(categoryList[0]);
// provide("categoryList", categoryList);
</script>

<style></style>

<template>
  <div id="app">
    <app-header></app-header>
    <router-view></router-view>
    <app-footer></app-footer>
  </div>
</template>
