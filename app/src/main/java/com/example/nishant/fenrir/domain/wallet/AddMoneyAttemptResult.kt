package com.example.nishant.fenrir.domain.wallet

sealed class AddMoneyAttemptResult {

    object Success : AddMoneyAttemptResult()

    sealed class Failure : AddMoneyAttemptResult() {

        object NoInternet : Failure()
    }
}