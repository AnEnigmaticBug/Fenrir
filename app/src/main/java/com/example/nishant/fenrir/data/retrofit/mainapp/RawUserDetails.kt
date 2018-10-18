package com.example.nishant.fenrir.data.retrofit.mainapp

import com.google.gson.annotations.SerializedName

data class RawUserDetails(@SerializedName("name") val name: String,
                          @SerializedName("qr_code") val qrCodeContent: String)