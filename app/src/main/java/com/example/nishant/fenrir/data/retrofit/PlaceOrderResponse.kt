package com.example.nishant.fenrir.data.retrofit

import com.google.gson.annotations.SerializedName

data class PlaceOrderResponse(
        @SerializedName("order_id") val orderId: String,
        @SerializedName("message") val message: String? = null,
        @SerializedName("unavailable") val invalidItemIds: List<String>?)