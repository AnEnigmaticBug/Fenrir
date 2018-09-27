package com.example.nishant.fenrir.screens.wallet.items

import com.example.nishant.fenrir.domain.wallet.Item
import org.junit.Assert.*
import org.junit.Test

class RawItemTest {

    @Test
    fun test_FromItem() {
        val item = Item("98", "Brownie", 50, "34")
        assertEquals(RawItem("98", "Brownie", "INR 50"), RawItem.fromItem(item))
    }
}