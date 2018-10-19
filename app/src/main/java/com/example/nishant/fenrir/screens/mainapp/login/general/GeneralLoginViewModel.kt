package com.example.nishant.fenrir.screens.mainapp.login.general

import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.mainapp.LoginRepository
import io.reactivex.schedulers.Schedulers

class GeneralLoginViewModel(private val lRepo: LoginRepository) : ViewModel() {

    fun login(jwtToken: String) {
        lRepo.loginBitsian(jwtToken)
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}