package com.example.nishant.fenrir.screens.wallet.items.addtocart

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.Item
import javax.inject.Inject

class AddToCartViewModelFactory(private val stallId: String, private val itemId: String) : ViewModelProvider.Factory {

    @Inject
    lateinit var walletRepository: WalletRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddToCartViewModel(walletRepository, stallId, itemId) as T
    }
}