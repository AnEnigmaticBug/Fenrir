package com.example.nishant.fenrir.data.repository.mainapp

import android.annotation.SuppressLint
import android.util.Log
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.data.retrofit.NetworkWatcher
import com.example.nishant.fenrir.data.retrofit.mainapp.LoginService
import com.example.nishant.fenrir.domain.UserDetails
import com.example.nishant.fenrir.domain.mainapp.LoginAttemptResult
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.JsonObject
import io.reactivex.Single

class LoginRepositoryImpl(private val networkWatcher: NetworkWatcher, private val cRepo: CentralRepository, private val loginService: LoginService) : LoginRepository {

    @SuppressLint("CheckResult")
    override fun loginOutstee(username: String, password: String): Single<LoginAttemptResult> {
        if(!networkWatcher.checkIfConnectedToInternet()) {
            return Single.just(LoginAttemptResult.Failure.NoInternet)
        }

        val loginRequestBody = JsonObject().also {
            it.addProperty("username", username)
            it.addProperty("password", password)
            it.addProperty("is_bitsian", false)
        }

        return loginWithRequestBody(loginRequestBody, false)
    }

    override fun loginBitsian(jwtToken: String): Single<LoginAttemptResult> {
        if(!networkWatcher.checkIfConnectedToInternet()) {
            return Single.just(LoginAttemptResult.Failure.NoInternet)
        }

        val loginRequestBody = JsonObject().also {
            it.addProperty("id_token", jwtToken)
            it.addProperty("is_bitsian", true)
        }

        return loginWithRequestBody(loginRequestBody, true)
    }

    private fun loginWithRequestBody(reqBody: JsonObject, isBITSian: Boolean): Single<LoginAttemptResult> {
        return loginService.login(reqBody.also { it.addProperty("registration_token", FirebaseInstanceId.getInstance().token) })
                .flatMap {
                    when(it.code()) {
                        200  -> {
                            loginService.getUserDetails("JWT ${it.body()!!.jwtToken}").subscribe { _userDetails ->
                                cRepo.setUserDetails(UserDetails(it.body()!!.id, "JWT ${it.body()!!.jwtToken}", _userDetails.body()!!.name, isBITSian, "", _userDetails.body()!!.qrCodeContent)).subscribe()
                            }
                            Single.just(LoginAttemptResult.Success)
                        }
                        404  -> Single.just(LoginAttemptResult.Failure.NoSuchUser)
                        else -> throw IllegalStateException("Unknown Http status code: ${it.code()}")
                    }
                }
    }
}