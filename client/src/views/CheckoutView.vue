<script setup lang="ts">
import { reactive, Ref } from "vue";
import useVuelidate from "@vuelidate/core";
import { asDollarsAndCents } from "@/main";
import { helpers, maxLength, minLength, required } from "@vuelidate/validators";
import { isMobilePhone, isCreditCard, isEmailValid } from "@/utils";
import { useCartStore } from "@/stores/CartStore";
import router from "@/router";
const cartStore = useCartStore();
const cart = cartStore.cart;
// import { isCreditCard, isMobilePhone } from "@/utils";
// import CheckoutFieldError from "@/components/CheckoutFieldError.vue";
// import router from "@/router";

const months: string[] = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const form = reactive({
  name: "",
  address: "",
  phone: "",
  email: "",
  ccNumber: "",
  ccExpiryMonth: new Date().getMonth() + 1,
  ccExpiryYear: new Date().getFullYear(),
  checkoutStatus: "",
});

const rules = {
  name: {
    required: helpers.withMessage("Please provide a name.", required),
    minLength: helpers.withMessage(
      "Name must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Name can have at most 45 letters.",
      maxLength(45)
    ),
  },
  address: {
    required: helpers.withMessage("Please provide address.", required),
    minLength: helpers.withMessage("Please provide a address", minLength(4)),
  },
  phone: {
    required: helpers.withMessage("Please provide a  phone number", required),
    phone: helpers.withMessage(
      "Please provide a valid phone number",
      isMobilePhone
    ),
  },
  ccNumber: {
    required: helpers.withMessage(
      "Please provide a  valid credit card number",
      required
    ),
    ccNumber: helpers.withMessage(
      "Please provide a valid credit card number",
      isCreditCard
    ),
  },
  email: {
    required: helpers.withMessage("Please provide a  valid email id", required),
    email: helpers.withMessage("Please provide a valid email id", isEmailValid),
  },
  ccExpiryMonth: {},
  ccExpiryYear: {},
  //   TODO: Add more validations for these and other fields that need more validation.
};
const $v = useVuelidate(rules, form);

async function submitOrder() {
  console.log("Submit order");
  const isFormCorrect = await $v.value.$validate();
  if (!isFormCorrect) {
    form.checkoutStatus = "ERROR";
  } else {
    form.checkoutStatus = "PENDING";
    console.log(form.ccExpiryYear);
    await cartStore
      .placeOrder({
        name: form.name,
        address: form.address,
        phone: form.phone,
        email: form.email,
        ccNumber: form.ccNumber,
        ccExpiryMonth: form.ccExpiryMonth,
        ccExpiryYear: form.ccExpiryYear,
      })
      .then(() => {
        form.checkoutStatus = "OK";
        router.push({ name: "confirmation-view" });
      })
      .catch((reason) => {
        form.checkoutStatus = "SERVER_ERROR";
        console.log("Error placing order", reason);
      });
  }
}

/* NOTE: For example yearFrom(0) == <current_year> */
function yearFrom(index: number) {
  return new Date().getFullYear() + index;
}
</script>

<style scoped>
/* TODO: Adapt these styles to your page */
.checkout-page {
  background: var(--primary-color);
  color: #100101;
}
.checkout-page-body {
  justify-content: center;
  display: flex;
  padding: 1em;
}

form {
  display: flex;
  flex-direction: column;
}

form > div {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 0.5em;
}

form > div > div {
  display: flex;
  justify-content: flex-end;
}

form > div > div > input,
form > div > div > select {
  background-color: #ffffff;
  margin-left: 0.5em;
}

form > div > input,
form > div > select {
  background-color: #ffffff;
  margin-left: 0.5em;
}

form > div > .error {
  color: red;
  text-align: right;
  font-size: 13px;
}

.main-card:hover > * {
  webkit-filter: blur(1px); /* Chrome, Safari, Opera */
  filter: blur(1px);
}

.checkoutStatusBox {
  margin: 1em;
  padding: 1em;
  text-align: center;
}

.button-confirm,
.button-confirm:visited {
  display: inline-block;
  background: #8a5203;
  color: rgb(255, 255, 255);
  text-decoration: none;
  border-radius: 5px;
  padding: 0.5em 1em;
  cursor: pointer;
}

.button-confirm:hover,
.button-confirm:active {
  background: #cda1a1;
  color: rgb(16, 1, 1);
}
</style>

<template>
  <div class="checkout-page">
    <section class="checkout-page-body" v-if="!cart.empty">
      <form
        style="text-align: right; display: inline-block"
        @submit.prevent="submitOrder"
      >
        <div style="display: flex; flex-direction: column">
          <div>
            <label for="name">Name</label>
            <input
              type="text"
              size="20"
              id="name"
              name="name"
              v-model.lazy="$v.name.$model"
            />
          </div>
          <template v-if="$v.name.$error">
            <span
              class="error"
              v-for="error of $v.name.$errors.slice(0, 1)"
              :key="error.$uid"
              >{{ error.$message }}</span
            >
          </template>
        </div>
        <div style="display: flex; flex-direction: column">
          <div>
            <label for="address">Address</label>
            <input
              type="text"
              size="20"
              id="name"
              name="name"
              v-model.lazy="$v.address.$model"
            />
          </div>
          <template v-if="$v.address.$error">
            <span
              class="error"
              v-for="error of $v.address.$errors.slice(0, 1)"
              :key="error.$uid"
              >{{ error.$message }}</span
            >
          </template>
        </div>
        <div style="display: flex; flex-direction: column">
          <div>
            <label for="phone">Phone</label>
            <input
              class="textField"
              type="text"
              size="20"
              id="phone"
              name="phone"
              v-model.lazy="$v.phone.$model"
            />
          </div>
          <template v-if="$v.phone.$error">
            <span
              class="error"
              v-for="error of $v.phone.$errors.slice(0, 1)"
              :key="error.$uid"
              >{{ error.$message }}</span
            >
          </template>
        </div>
        <div style="display: flex; flex-direction: column">
          <div>
            <label for="email">Email</label>
            <input
              type="text"
              size="20"
              id="email"
              name="email"
              v-model.lazy="$v.email.$model"
            />
          </div>
          <template v-if="$v.email.$error">
            <span
              class="error"
              v-for="error of $v.email.$errors.slice(0, 1)"
              :key="error.$uid"
              >{{ error.$message }}</span
            >
          </template>
        </div>
        <div style="display: flex; flex-direction: column">
          <div>
            <label for="ccNumber">Credit card</label>
            <input
              type="text"
              size="20"
              id="ccNumber"
              name="ccNumber"
              v-model.lazy="$v.ccNumber.$model"
            />
          </div>
          <template v-if="$v.ccNumber.$error">
            <span
              class="error"
              v-for="error of $v.ccNumber.$errors.slice(0, 1)"
              :key="error.$uid"
              >{{ error.$message }}</span
            >
          </template>
        </div>
        <div>
          <label>Expiry</label>
          <select v-model.lazy="$v.ccExpiryMonth.$model">
            <option
              v-for="(month, index) in months"
              :key="index"
              :value="index + 1"
            >
              {{ month }} ({{ index + 1 }})
            </option>
          </select>
          <select v-model.lazy="$v.ccExpiryYear.$model">
            <option :key="0" :value="yearFrom(0)">{{ yearFrom(0) }}</option>
            <option
              v-for="year_next in 15"
              :key="year_next"
              :value="yearFrom(year_next)"
            >
              {{ yearFrom(year_next) }}
            </option>
          </select>
        </div>

        <p>
          SubTotal
          {{ asDollarsAndCents(cart.subtotal) }}
        </p>

        <p>
          Surcharge
          {{ asDollarsAndCents(cart.surcharge) }}
        </p>

        <p>
          Total
          {{ asDollarsAndCents(cart.surcharge + cart.subtotal) }}
        </p>

        <input
          type="submit"
          name="submit"
          class="button-confirm"
          :disabled="form.checkoutStatus === 'PENDING'"
          @click="submitOrder()"
          value="Complete Purchase"
        />
        <section v-show="form.checkoutStatus !== ''" class="checkoutStatusBox">
          <div v-if="form.checkoutStatus === 'ERROR'" style="color: red">
            Fix Errors Above
          </div>
          <div v-else-if="form.checkoutStatus === 'PENDING'">Processing...</div>

          <div v-else-if="form.checkoutStatus === 'OK'">Order placed...</div>

          <div v-else>An unexpected error occurred, please try again.</div>
        </section>
      </form>
    </section>
    <section v-else style="justify-content: center">
      <div style="text-align: center">
        <div>
          Add Items to cart to continue to checkout. To shop click continue
        </div>
        <router-link :to="'/category/' + cartStore.selectedCategoryName">
          <button class="button-confirm">Continue Shopping</button>
        </router-link>
      </div>
    </section>
  </div>
</template>
