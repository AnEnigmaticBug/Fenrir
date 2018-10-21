package com.example.nishant.fenrir.screens.wallet.tracking.trackorder

sealed class NotifyOTPStatus {

    object Idle : NotifyOTPStatus()

    object InProgress : NotifyOTPStatus()

    object Success : NotifyOTPStatus()

    class Failure(val message: String) : NotifyOTPStatus()

}