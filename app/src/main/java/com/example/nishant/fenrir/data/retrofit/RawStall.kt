package com.example.nishant.fenrir.data.retrofit

import com.google.gson.annotations.SerializedName

data class RawStall(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String)