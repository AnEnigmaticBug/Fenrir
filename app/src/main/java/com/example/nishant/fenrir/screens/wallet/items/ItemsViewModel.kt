package com.example.nishant.fenrir.screens.wallet.items

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.CartEntry
import com.example.nishant.fenrir.domain.wallet.Item
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable

class ItemsViewModel(private val wRepo: WalletRepository, private val stallId: String) : ViewModel() {

    val rawItems: LiveData<List<RawItem>> = MutableLiveData()

    private val d1 = CompositeDisposable()

    init {
        d1.set(wRepo.getAllItemsInStallOfId(stallId)
                .subscribe { _items ->
                    rawItems.toMut().postValue(_items.filter { it.isAvailable }.map { RawItem.fromItem(it) })
                })
    }

    fun addItemToCart(itemId: String, quantity: Int) {
        val rawItem = rawItems.value!!.find { it.id == itemId }!!
        wRepo.addEntryToCart(CartEntry(itemId, Item(itemId, rawItem.name, rawItem.price.substring(4).toInt(), true, stallId), quantity, true))
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}