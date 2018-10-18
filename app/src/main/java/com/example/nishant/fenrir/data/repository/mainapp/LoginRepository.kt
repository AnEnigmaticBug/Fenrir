package com.example.nishant.fenrir.data.repository.mainapp

import com.example.nishant.fenrir.domain.mainapp.LoginAttemptResult
import io.reactivex.Single

interface LoginRepository {

    fun loginOutstee(username: String, password: String): Single<LoginAttemptResult>

    fun loginBitsian(jwtToken: String): Single<LoginAttemptResult>
}