package com.example.nishant.fenrir.screens.wallet.tracking.trackorder

import com.example.nishant.fenrir.domain.wallet.TrackingStatus

sealed class TrackingScreenRow {

    class Stall(val entryId: String, val name: String, val otp: Int?, val otpShown: Boolean) : TrackingScreenRow() {

        override fun toString(): String {
            return "$name with $otp"
        }
    }
    class Entry(val entryId: String, val itemName: String, val priceAndQuantity: String, val status: TrackingStatus) : TrackingScreenRow() {

        override fun toString(): String {
            return "$entryId:$itemName"
        }
    }
}