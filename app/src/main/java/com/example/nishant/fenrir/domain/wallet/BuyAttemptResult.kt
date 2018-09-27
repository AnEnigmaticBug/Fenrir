package com.example.nishant.fenrir.domain.wallet

sealed class BuyAttemptResult {

    object Success : BuyAttemptResult()

    sealed class Failure : BuyAttemptResult() {

        object NoInternet : Failure()

        object VacantCart : Failure()

        object LessBudget : Failure()

        class OutOfStock(val itemIds: List<String>) : Failure()
    }
}