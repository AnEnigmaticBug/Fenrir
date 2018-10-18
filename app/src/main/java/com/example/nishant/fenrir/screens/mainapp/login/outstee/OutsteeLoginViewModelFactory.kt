package com.example.nishant.fenrir.screens.mainapp.login.outstee

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.mainapp.LoginRepository
import javax.inject.Inject

class OutsteeLoginViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var loginRepository: LoginRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OutsteeLoginViewModel(loginRepository) as T
    }
}