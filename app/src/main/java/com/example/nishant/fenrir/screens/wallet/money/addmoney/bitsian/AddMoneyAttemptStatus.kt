package com.example.nishant.fenrir.screens.wallet.money.addmoney.bitsian

sealed class AddMoneyAttemptStatus {

    object Success : AddMoneyAttemptStatus()

    class Failure(val message: String) : AddMoneyAttemptStatus()
}