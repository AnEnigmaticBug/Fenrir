package com.example.nishant.fenrir.domain.wallet

sealed class NotifyOTPResult {

    object Success : NotifyOTPResult()

    sealed class Failure : NotifyOTPResult() {

        object NoInternet : Failure()

        object ServerBusy : Failure()
    }
}