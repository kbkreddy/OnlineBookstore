<script setup lang="ts"></script>

<style scoped>
input[type="text"] {
  margin: 0.5em 0;
  width: 12em;
}

.search-bar {
  padding: 0.5em;
}
</style>

<template>
  <section class="title-and-search-bar">
    <form action="category.html" style="position: relative">
      <div>
        <input
          type="text"
          class="search-bar"
          style="
            border-radius: 5px;
            margin-left: 2.5em;
            margin-top: 3.5em;
            background-color: #d4c3c3;
            width: 20em;
            font-style: italic;
          "
          placeholder="     Search for Books, Authors, Titles..."
        />
        <div
          style="
            position: absolute;
            top: 0px;
            margin-top: 67px;
            margin-left: 50px;
          "
        >
          <i class="fa-solid fa-magnifying-glass"></i>
        </div>
      </div>
    </form>
  </section>
</template>
