package com.example.nishant.fenrir.data.retrofit.wallet

import com.google.gson.annotations.SerializedName

data class RawItem(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("price") val price: Int,
        @SerializedName("is_available") val isAvailable: Boolean)