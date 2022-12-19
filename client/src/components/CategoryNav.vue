<script setup lang="ts">
import { inject } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
// const categoryList = inject("categoryList");
import { useCategoryStore } from "@/stores/CategoryStore";
import { useCartStore } from "@/stores/CartStore";
const cartStore = useCartStore();
const setCurrentCategoryName = function (categoryName: string) {
  cartStore.selectedCategoryName = categoryName;
};

const categoryStore = useCategoryStore();
</script>

<style scoped>
.category-buttons {
  display: flex;
  flex-direction: row;
  text-align: center;
  background-color: var(--neutral-color);
}

.button.selected-category-button {
  background-color: var(--primary-color);
  color: #111111;
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: var(--neutral-color);
  color: white;
}

.button.unselected-category-button:hover,
.button.unselected-category-button:active {
  background-color: var(--primary-color);
  color: #111111;
}
</style>

<template>
  <nav class="category-nav">
    <ul class="category-buttons">
      <li
        style="width: 25%"
        v-for="category in categoryStore.categoryList"
        :key="category.categoryId"
        @click="setCurrentCategoryName(category.name)"
      >
        <router-link
          style="width: 280px"
          :to="{ name: 'category-view', params: { name: category.name } }"
          class="button"
          :class="
            category.name === route.params.name
              ? 'selected-category-button'
              : 'unselected-category-button'
          "
        >
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </nav>
</template>
