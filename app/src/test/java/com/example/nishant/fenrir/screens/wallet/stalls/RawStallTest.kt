package com.example.nishant.fenrir.screens.wallet.stalls

import com.example.nishant.fenrir.domain.wallet.Stall
import org.junit.Assert.*
import org.junit.Test

class RawStallTest {

    @Test
    fun test_FromStall() {
        val stall = Stall("11", "Goosebumps", "A brownie stall")
        assertEquals(RawStall("11", "Goosebumps"), RawStall.fromStall(stall))
    }
}