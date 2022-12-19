<script setup lang="ts">
import ConfirmationTable from "@/components/ConfirmationTable";

import { useCategoryStore } from "@/stores/CategoryStore";
const categoryStore = useCategoryStore();

import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";
import { computed } from "vue";
const orderDetailsStore = useOrderDetailsStore();
const orderDetails = orderDetailsStore.orderDetails;

const orderDate = computed(function () {
  let date = new Date(orderDetails.order.dateCreated);
  return date.toLocaleTimeString();
});

const ccExpDate = computed(function (): Date {
  return new Date(orderDetails.customer.ccExpDate);
});

const ccExpYear = computed(function (): number {
  return ccExpDate.value.getFullYear();
});

const ccExpMonth = computed(function (): string {
  let month = ccExpDate.value.getMonth() + 1;
  let result = "" + month;
  if (month < 10) {
    result = "0" + month;
  }
  return result;
});
</script>

<style scoped>
#confirmationView {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1em auto;
  color: black;
}

#confirmationId {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  background-color: var(--primary-color-dark);
  border-radius: 5px;
  padding: 5px;
}
td {
  padding: 5px;
}

ul {
  text-align: left;
}
ul > li {
  margin: 0.25em;
}
.success-image {
  height: 100px;
  margin-left: 14em;
}
</style>

<template>
  <div id="confirmationView" style="background-color: #ffe8d1">
    <div style="align-items: center">
      <div>
        <img
          class="success-image"
          :src="require('@/assets/myimages/site/success.png')"
          style=""
        />
        <h1 style="margin-left: 2em">Thank you for your Order!!!</h1>
        <p style="margin: 10px">
          Your Order is received. Your order has been received and is being
          processed.
        </p>
      </div>
      <div>
        <h2 style="margin-left: 8em">Order Summary</h2>
        <confirmation-table> </confirmation-table>
      </div>
      <div style="display: flex; flex-direction: column">
        <h2 style="margin-left: 8em">Order Details</h2>
        <div id="confirmationId">
          <p>
            <strong>Confirmation:</strong> #{{
              orderDetails.order.confirmationNumber
            }}
          </p>
          <p>
            <strong>Order Time: </strong>
            {{
              new Date(orderDetails.order.dateCreated).getHours() +
              ":" +
              new Date(orderDetails.order.dateCreated).getMinutes()
            }}
          </p>
          <p>
            <strong>Order Date:</strong>
            {{
              new Date(orderDetails.order.dateCreated).toLocaleDateString(
                "en-US"
              )
            }}
          </p>
        </div>
      </div>
    </div>
    <div>
      <table>
        <tr>
          <td><strong>Name</strong></td>
          <td>{{ orderDetails.customer.customerName }}</td>
        </tr>
        <tr>
          <td><strong>Address</strong></td>
          <td>{{ orderDetails.customer.address }}</td>
        </tr>
        <tr>
          <td><strong>Email</strong></td>
          <td>{{ orderDetails.customer.email }}</td>
        </tr>
        <tr>
          <td><strong>Mobile Number</strong></td>
          <td>{{ orderDetails.customer.phone }}</td>
        </tr>
        <tr>
          <td><strong>Credit card number</strong></td>
          <td>
            ** ** **
            {{
              orderDetails.customer.ccNumber.slice(
                orderDetails.customer.ccNumber.length - 4,
                orderDetails.customer.ccNumber.length
              )
            }}
            {{
              "(" +
              new Date(orderDetails.customer.ccExpDate).getMonth() +
              "/" +
              new Date(orderDetails.customer.ccExpDate).getFullYear() +
              ")"
            }}
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
