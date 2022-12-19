<script setup lang="ts">
import CartTable from "@/components/CartTable.vue";
import { useCartStore } from "@/stores/CartStore";
const cartStore = useCartStore();
const clearCart = function () {
  cartStore.cart.clear();
};
</script>
<style scoped>
h1 {
  padding: 1em;
  text-align: center;
}
</style>

<template>
  <div style="background-color: #ffe8d1">
    <h1>Cart Page</h1>
    <cart-table></cart-table>
  </div>
</template>
