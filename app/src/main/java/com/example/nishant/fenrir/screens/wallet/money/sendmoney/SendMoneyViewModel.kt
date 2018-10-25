package com.example.nishant.fenrir.screens.wallet.money.sendmoney

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.TransferMoneyAttemptResult
import com.example.nishant.fenrir.util.toMut
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SendMoneyViewModel(private val wRepo: WalletRepository) : ViewModel() {

    val transferStatus: LiveData<TransferMoneyAttemptStatus> = MutableLiveData()
    var qrCodeContents: String = ""

    @SuppressLint("CheckResult")
    fun transferMoney(recipientId: String, amount: Int) {
        wRepo.transferMoney(recipientId, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            when(result) {
                                is TransferMoneyAttemptResult.Success -> transferStatus.toMut().value = TransferMoneyAttemptStatus.Success
                                is TransferMoneyAttemptResult.Failure -> {
                                    when(result) {
                                        is TransferMoneyAttemptResult.Failure.NoInternet -> transferStatus.toMut().value = TransferMoneyAttemptStatus.Failure("No internet found")
                                        is TransferMoneyAttemptResult.Failure.SwdProblem -> transferStatus.toMut().value = TransferMoneyAttemptStatus.Failure("Transfer isn't allowed between BITSians and Outstation participants")
                                        is TransferMoneyAttemptResult.Failure.SendToSelf -> transferStatus.toMut().value = TransferMoneyAttemptStatus.Failure("Can't transfer money to oneself")
                                    }
                                }
                            }
                        },
                        {
                            transferStatus.toMut().value = TransferMoneyAttemptStatus.Failure("Error! Please try again")
                        }
                )
    }
}