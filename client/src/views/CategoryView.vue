<script setup lang="ts">
import { watch } from "vue";
import CategoryNav from "@/components/CategoryNav.vue";
import CategoryBookList from "@/components/CategoryBookList.vue";
import { useRoute } from "vue-router";
import { useBookStore } from "@/stores/BookStore";
import router from "@/router";
const route = useRoute();
const bookStore = useBookStore();

watch(
  () => route.params.name,
  (newName) => {
    bookStore.fetchBooks(newName as string).catch(() => {
      router.push("/not-found");
    });
  },
  { immediate: true }
);
</script>

<style scoped></style>

<template>
  <div class="category-page" style="position: relative">
    <category-nav></category-nav>
    <section class="category-book-list">
      <category-book-list></category-book-list>
    </section>
    <div style="position: absolute; bottom: 5px; right: 30px">
      <Button style="border: none; background-color: transparent">
        <i class="fa-solid fa-angle-left"></i> Page 1 of 20
        <i class="fa-solid fa-angle-right"></i
      ></Button>
    </div>
  </div>
</template>
