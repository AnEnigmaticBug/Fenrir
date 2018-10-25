package com.example.nishant.fenrir.screens.mainapp.login.outstee

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.widget.Toast
import com.example.nishant.fenrir.data.repository.mainapp.LoginRepository
import com.example.nishant.fenrir.domain.mainapp.LoginAttemptResult
import com.example.nishant.fenrir.util.toMut
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OutsteeLoginViewModel(private val lRepo: LoginRepository) : ViewModel() {

    val loginStatus: LiveData<LoginAttemptStatus> = MutableLiveData()

    init {
        loginStatus.toMut().value = LoginAttemptStatus.Idle
    }

    @SuppressLint("CheckResult")
    fun login(username: String, password: String) {
        loginStatus.toMut().value = LoginAttemptStatus.InProgress
        lRepo.loginOutstee(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            when(result) {
                                is LoginAttemptResult.Success -> loginStatus.toMut().value = LoginAttemptStatus.Success
                                is LoginAttemptResult.Failure -> {
                                    when(result) {
                                        is LoginAttemptResult.Failure.NoInternet -> loginStatus.toMut().value = LoginAttemptStatus.Failure("No internet found")
                                        is LoginAttemptResult.Failure.NoSuchUser -> loginStatus.toMut().value = LoginAttemptStatus.Failure("Wrong Credentials")
                                    }
                                }
                            }
                        },
                        {
                            loginStatus.toMut().value = LoginAttemptStatus.Failure("Internet too slow")
                        }
                )
    }
}