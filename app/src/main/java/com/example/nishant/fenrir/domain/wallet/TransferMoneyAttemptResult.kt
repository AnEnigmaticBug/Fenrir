package com.example.nishant.fenrir.domain.wallet

sealed class TransferMoneyAttemptResult {

    object Success : TransferMoneyAttemptResult()

    sealed class Failure : TransferMoneyAttemptResult() {

        object NoInternet : Failure()

        object SwdProblem : Failure()

        object SendToSelf : Failure()
    }
}