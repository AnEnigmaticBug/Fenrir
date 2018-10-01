package com.example.nishant.fenrir.screens.wallet.tracking.trackorder

import com.example.nishant.fenrir.domain.wallet.TrackingStatus

data class RawTrackedEntry(val id: String, val itemName: String, val stallName: String, val priceAndQuantity: String, val status: TrackingStatus)