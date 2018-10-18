package com.example.nishant.fenrir.data.retrofit.mainapp

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {

    @POST("2018/shop/auth/")
    fun login(@Body body: JsonObject): Single<Response<IdAndJwtHolder>>

    @GET("2018/shop/get-profile/")
    fun getUserDetails(@Header("Authorization") jwtToken: String): Single<Response<RawUserDetails>>
}