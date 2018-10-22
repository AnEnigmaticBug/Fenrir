package com.example.nishant.fenrir.screens.wallet.money.addmoney.bitsian

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import javax.inject.Inject

class AddMoneyBitsianViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var walletRepository: WalletRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddMoneyBitsianViewModel(walletRepository) as T
    }
}