package com.example.nishant.fenrir.data.retrofit.mainapp

import com.google.gson.annotations.SerializedName

data class IdAndJwtHolder(@SerializedName("user_id") val id: String,
                          @SerializedName("token") val jwtToken: String)