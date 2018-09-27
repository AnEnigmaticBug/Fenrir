package com.example.nishant.fenrir.screens.wallet.stalls

import com.example.nishant.fenrir.domain.wallet.Stall

data class RawStall(val id: String, val name: String) {

    companion object {

        fun fromStall(stall: Stall) = RawStall(stall.id, stall.name)
    }
}