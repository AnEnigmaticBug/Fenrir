package com.example.nishant.fenrir.screens.wallet.items

import com.example.nishant.fenrir.domain.wallet.Item

data class RawItem(val id: String, val name: String, val price: String) {

    companion object {

        fun fromItem(item: Item) = RawItem(item.id, item.name, "INR ${item.price}")
    }
}