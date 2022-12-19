<script setup lang="ts">
import { useCategoryStore } from "@/stores/CategoryStore";
const categoryStore = useCategoryStore();
import { useCartStore } from "@/stores/CartStore";
const cartStore = useCartStore();
const setCurrentCategoryName = function (categoryName: string) {
  cartStore.selectedCategoryName = categoryName;
};
</script>

<style scoped>
.header-dropdown {
  position: relative;
}

.categories-button {
  background-color: var(--primary-color-dark);
  margin-top: 0.25em;
}

.header-dropdown ul {
  background-color: rgb(253, 209, 209);
  color: black;
  display: none;
}

.header-dropdown li {
  padding: 0.25em 0.5em;
}

.header-dropdown a {
  color: black;
  text-decoration: none;
}

.header-dropdown li:hover {
  background: rgb(109, 110, 3);
  color: #ffffff;
}

.header-dropdown:hover ul {
  display: block;
  position: absolute;
  min-width: 8em;
  z-index: 1;
  cursor: pointer;
}
</style>

<template>
  <section class="header-dropdown-and-cart">
    <div style="margin-bottom: -0.2em; margin-left: 10em">
      <button
        class="button"
        style="
          font-size: 20px;
          padding-bottom: 5px;
          background-color: #3c2727;
          border-style: none;
          border-color: #3c2727;
          color: rgb(255, 255, 255);
        "
      >
        <i class="fa-solid fa-user" style="color: #ffffff"></i>
        KBK
      </button>
      <router-link to="/cart"
        ><button
          class="button"
          style="
            position: relative;
            font-size: 25px;
            background-color: #3c2727;
            border-style: none;
            border-color: #3c2727;
            color: rgb(255, 255, 255);
          "
        >
          <p
            style="
              position: absolute;
              color: #000;
              bottom: 22px;
              margin-left: 12px;
              font-size: 14px;
              font-weight: bold;
            "
          >
            {{ cartStore.count }}
          </p>
          <i class="fa-solid fa-cart-shopping" style="color: #ffffff"></i>
        </button>
      </router-link>
    </div>
    <div class="header-dropdown">
      <button class="button categories-button">
        Categories <i class="fa-solid fa-caret-down"></i>
      </button>
      <ul>
        <li
          v-for="category in categoryStore.categoryList"
          :key="category.categoryId"
          @click="setCurrentCategoryName(category.name)"
        >
          <router-link :to="'../category/' + category.name">
            {{ category.name }}
          </router-link>
        </li>
      </ul>
    </div>
  </section>
</template>
