package com.example.nishant.fenrir.screens.wallet.cart

sealed class BuyAttemptStatus {

    object Idle : BuyAttemptStatus()

    object InProgress : BuyAttemptStatus()

    object Success : BuyAttemptStatus()

    class Failure(val message: String) : BuyAttemptStatus() {

        override fun equals(other: Any?): Boolean {
            if(other is Failure) {
                if(this.message == other.message)  {
                    return true
                }
                return false
            }
            return false
        }

        override fun hashCode(): Int {
            return message.length
        }
    }
}