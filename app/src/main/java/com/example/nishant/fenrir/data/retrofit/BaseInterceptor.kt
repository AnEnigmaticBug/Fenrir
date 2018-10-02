package com.example.nishant.fenrir.data.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val modifiedRequest = chain!!.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Wallet-Token", "asdf")
                .addHeader("x-origin", "Android")
                .build()
        return chain.proceed(modifiedRequest)
    }
}