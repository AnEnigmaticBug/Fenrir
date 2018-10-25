package com.example.nishant.fenrir.screens.wallet.money.addmoney.bitsian

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.AddMoneyAttemptResult
import com.example.nishant.fenrir.util.toMut
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddMoneyBitsianViewModel(private val wRepo: WalletRepository) : ViewModel() {

    val addMoneyStatus: LiveData<AddMoneyAttemptStatus> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun addMoney(amount: Int) {
        if(amount <= 0) {
            addMoneyStatus.toMut().value = AddMoneyAttemptStatus.Failure("Please enter a positive amount")
            return
        }
        wRepo.addMoney(amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            when(result) {
                                is AddMoneyAttemptResult.Success -> addMoneyStatus.toMut().value = AddMoneyAttemptStatus.Success
                                is AddMoneyAttemptResult.Failure -> {
                                    when(result) {
                                        is AddMoneyAttemptResult.Failure.NoInternet -> addMoneyStatus.toMut().value = AddMoneyAttemptStatus.Failure("No internet found")
                                    }
                                }
                            }
                        },
                        {
                            addMoneyStatus.toMut().value = AddMoneyAttemptStatus.Failure("Error! Please try again")
                        }
                )
    }
}