<script setup lang="ts">
import HeaderDropdown from "@/components/HeaderDropdown.vue";
import HeaderSearchBar from "@/components/HeaderSearchBar";
</script>

<style scoped>
header {
  background: var(--secondary-background-color);
  display: flex;
  flex-direction: row;
  padding: 1em;
  gap: 1em;
}

.text-logo {
  font-family: var(--title-font-family);
}

.logo-text {
  width: 19em;
}

@media (max-width: 600px) {
  header {
    flex-direction: column;
  }
}
</style>

<template>
  <head>
    <title>My Bookstore</title>
    <meta charset="utf-8" />
    <meta name="description" content="The homepage for My Bookstore" />

    <link
      href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
      rel="stylesheet"
    />

    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
    />

    <link rel="stylesheet" href="css/global.css" />

    <link rel="stylesheet" href="css/app.css" />
    <link rel="stylesheet" href="css/app-header.css" />
    <link rel="stylesheet" href="css/header-dropdown.css" />
    <link rel="stylesheet" href="css/app-footer.css" />
    <link rel="stylesheet" href="css/home.css" />
    <link rel="stylesheet" href="css/category-book-list.css" />
  </head>
  <header class="container">
    <section
      class="bookstore-logo logo-text"
      style="background-color: var(--primary-color); padding-left: 1em"
    >
      <router-link style="text-decoration: none" to="/">
        <img
          style="margin-left: 700px; zoom: 10%"
          src="@/assets/myimages/site/logo-transperant.png"
          alt="Another Bookstore Logo"
        />
        <h1
          class="text-logo logo-text"
          style="font-size: 25px; margin-left: 2em; color: #000"
        >
          EASY READER
        </h1>
      </router-link>
    </section>
    <header-search-bar></header-search-bar>
    <header-dropdown></header-dropdown>
  </header>
</template>
