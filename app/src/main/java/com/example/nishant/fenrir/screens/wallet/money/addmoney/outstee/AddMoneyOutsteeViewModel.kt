package com.example.nishant.fenrir.screens.wallet.money.addmoney.outstee

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.util.toMut

class AddMoneyOutsteeViewModel(cRepo: CentralRepository) : ViewModel() {

    val qrCodeContent: LiveData<String> = MutableLiveData()

    init {
        cRepo.getUserDetails()
                .subscribe {
                    qrCodeContent.toMut().value = it.qrCodeContent
                }
    }
}