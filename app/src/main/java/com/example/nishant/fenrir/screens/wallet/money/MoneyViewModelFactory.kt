package com.example.nishant.fenrir.screens.wallet.money

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import javax.inject.Inject

class MoneyViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var centralRepository: CentralRepository

    @Inject
    lateinit var walletRepository: WalletRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoneyViewModel(centralRepository, walletRepository) as T
    }
}