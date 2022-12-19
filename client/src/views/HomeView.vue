<script setup lang="ts">
import HomeCategoryList from "@/components/HomeCategoryList.vue";
</script>

<style scoped>
.home-page {
  display: flex;
}
.welcome-text {
  width: 25em;
  color: black;
  font-weight: bold;
  background-image: url("~@/assets/myimages/site/books1.jpg");
}

.back-image {
  background-image: url("~@/assets/myimages/site/books1.jpg");
}

.category-image-items {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 1em;
}

.category-image-items a {
  text-align: center;
  cursor: pointer;
}

.category-image-items div {
  padding: 0.5em 0;
  background: rgba(0, 0, 0, 0.5); /* last # is percent opacity */
  color: white;
  transform: translateY(-2.25em);
  margin-bottom: -2em;
}
#book-boxes {
  display: flex;
  flex-wrap: wrap;
  padding: 1em;
  gap: 1em;
}

.book-box-homepage {
  display: flex;
  flex-direction: column;
  background-color: var(--card-background-color);
  padding: 1em;
  gap: 0.25em;
  width: 10em;
  height: 14em;
}

.book-title {
  font-weight: bold;
}

.book-author {
  font-style: italic;
}
</style>
<template>
  <div class="home-page">
    <section class="category-book-list" style="background-color: #d4c3c3">
      <div style="font-size: 20px; background-color: #cda1a1">
        <h1 style="padding-top: 0.5em; padding-left: 6px; padding-bottom: 5px">
          Best Sellers
        </h1>
      </div>
      <ul id="book-boxes">
        <li class="book-box-homepage" style="position: relative">
          <div class="book-image">
            <a href="../">
              <img
                src="@/assets/myimages/books/91OAaFVVK-L.jpg"
                alt="The Iliad"
              />
            </a>
          </div>
          <div
            style="
              position: absolute;
              bottom: 8px;
              display: flex;
              flex-flow: column;
            "
          ></div>
        </li>
        <li class="book-box-homepage" style="position: relative">
          <div class="book-image">
            <a href="#">
              <img
                src="@/assets/myimages/books/81Iq-Av+ijL.jpg"
                alt="The Iliad"
              />
            </a>
          </div>
          <div
            style="
              position: absolute;
              bottom: 8px;
              display: flex;
              flex-flow: column;
            "
          ></div>
        </li>
        <li class="book-box-homepage" style="position: relative">
          <div class="book-image">
            <a href="#">
              <img
                src="@/assets/myimages/books/814dSvh3Q6L.jpg"
                alt="The Iliad"
              />
            </a>
          </div>
          <div
            style="
              bottom: 8px;
              display: flex;
              flex-flow: column;
              position: absolute;
            "
          ></div>
        </li>
        <li class="book-box-homepage" style="position: relative">
          <div class="book-image">
            <a href="#">
              <img
                src="@/assets/myimages/books/71rTaZat8wL.jpg"
                alt="The Iliad"
              />
            </a>
          </div>
          <div
            style="
              position: absolute;
              bottom: 8px;
              display: flex;
              flex-flow: column;
            "
          ></div>
        </li>
      </ul>
    </section>
    <section class="welcome-text flow-content container dark-background">
      <h2>Welcome to Easy Reader Bookstore</h2>
      <p>You are in the right place!</p>
      <p>
        Easy Reader is the world’s largest site for readers. Our mission is to
        help people find books they love. Currently Easy Reader supports 4
        categories mainly Mystery, Adventure, Fantasy, Romance.
      </p>

      <router-link
        to="/category/Fiction"
        class="button categories-button-cta"
        style="width: 15em; text-align: center; border: 3px solid #111111"
        >SHOP NOW!</router-link
      >
    </section>
  </div>
</template>
