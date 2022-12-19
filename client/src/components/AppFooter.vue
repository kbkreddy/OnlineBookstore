<script setup lang="ts"></script>

<style scoped>
footer {
  background: var(--secondary-background-color);
  display: flex;
  justify-content: space-between;
  gap: 1em;
  color: aliceblue;
}

.copyright,
.links,
.social-media-icons {
  display: flex;
  flex-direction: row;
}

.social-media-icons {
  text-align: center;
  gap: 0.25em;
}

.links {
  gap: 0.25em;
  align-self: center;
}
</style>
<template>
  <footer class="container">
    <section class="social-media-icons" style="margin-top: 9px">
      <i class="fa-brands fa-facebook fa-lg"></i>
      <i class="fa-brands fa-instagram-square fa-lg"></i>
      <i class="fa-brands fa-twitter fa-lg"></i>
      <i class="fa-brands fa-tiktok fa-lg"></i>
    </section>

    <section class="copyright">
      <p>
        <span class="fa-solid fa-copyright"></span> 2022 All Rights Reserved.
      </p>
    </section>

    <section class="links">
      <router-link to="/" style="color: #ffffff">ABOUT</router-link>
      <span> <strong>|</strong> </span>
      <router-link to="/" style="color: #ffffff">DIRECTIONS</router-link>
      <span> <strong>|</strong> </span>
      <router-link to="/" style="color: #ffffff">CONTACT</router-link>
    </section>
  </footer>
</template>
