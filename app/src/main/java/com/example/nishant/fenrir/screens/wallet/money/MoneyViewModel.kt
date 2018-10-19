package com.example.nishant.fenrir.screens.wallet.money

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable

class MoneyViewModel(cRepo: CentralRepository, wRepo: WalletRepository) : ViewModel() {

    val userDetails: LiveData<RawUserDetails> = MutableLiveData()
    val balance: LiveData<String> = MutableLiveData()
    val isBITSian: LiveData<Boolean> = MutableLiveData()

    private val d1 = CompositeDisposable()

    init {
        isBITSian.toMut().value = false
        cRepo.getUserDetails()
                .subscribe {
                    userDetails.toMut().value = RawUserDetails("ID: ${it.id}", it.name, it.profilePicURL)
                    isBITSian.toMut().value = it.isBITSian
                }
        d1.set(wRepo.getBalance()
                .subscribe {
                    balance.toMut().postValue("$it /-")
                })
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}