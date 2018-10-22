package com.example.nishant.fenrir.data.retrofit.mainapp

import com.google.gson.annotations.SerializedName

data class RawSignedEvent(@SerializedName("show_id") val id: String,
                          @SerializedName("show_name") val name: String,
                          @SerializedName("number_of_tickets") val numberOfTickets: Int)