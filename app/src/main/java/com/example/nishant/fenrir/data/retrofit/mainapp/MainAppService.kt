package com.example.nishant.fenrir.data.retrofit.mainapp

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MainAppService {

    @POST("2018/shop/get-tickets/")
    fun getSignedEvents(@Header("Authorization") jwtToken: String, @Body body: JsonObject): Single<Response<TicketsHolder>>
}