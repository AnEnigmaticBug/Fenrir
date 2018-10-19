package com.example.nishant.fenrir.screens.wallet.money.sendmoney

sealed class TransferMoneyAttemptStatus {

    object Success : TransferMoneyAttemptStatus()

    class Failure(val message: String) : TransferMoneyAttemptStatus()
}