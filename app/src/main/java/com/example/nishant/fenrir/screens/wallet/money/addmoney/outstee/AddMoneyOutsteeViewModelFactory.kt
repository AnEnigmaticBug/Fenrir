package com.example.nishant.fenrir.screens.wallet.money.addmoney.outstee

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.CentralRepository
import javax.inject.Inject

class AddMoneyOutsteeViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var centralRepository: CentralRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddMoneyOutsteeViewModel(centralRepository) as T
    }
}