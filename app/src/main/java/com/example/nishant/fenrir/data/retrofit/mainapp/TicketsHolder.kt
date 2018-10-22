package com.example.nishant.fenrir.data.retrofit.mainapp

import com.google.gson.annotations.SerializedName

data class TicketsHolder(@SerializedName("tickets") val events: List<RawSignedEvent>)