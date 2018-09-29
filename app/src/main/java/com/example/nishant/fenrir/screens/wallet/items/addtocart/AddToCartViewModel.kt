package com.example.nishant.fenrir.screens.wallet.items.addtocart

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.CartEntry
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class AddToCartViewModel(private val wRepo: WalletRepository, private val stallId: String, private val itemId: String) : ViewModel() {

    val itemName: LiveData<String> = MutableLiveData()
    val quantity: LiveData<Int> = MutableLiveData()
    val totalAmount: LiveData<String> = MutableLiveData()
    val messages: LiveData<String> = MutableLiveData()

    private var itemPrice: Int = 0

    private val d1 = CompositeDisposable()
    private val d2 = CompositeDisposable()

    init {
        quantity.toMut().value = 1
        d1.set(wRepo.getItemByIdInStallOfId(stallId, itemId)
                .subscribe {
                    itemPrice = it.price
                    itemName.toMut().postValue(it.name)
                    updateTotalAmount()
                })
    }

    fun incrementItemQuantity() {
        quantity.toMut().value = quantity.value!! + 1
        updateTotalAmount()
    }

    fun decrementItemQuantity() {
        if(quantity.value!! > 1) {
            quantity.toMut().value = quantity.value!! - 1
            updateTotalAmount()
        }
        else {
            messages.toMut().value = "You've to select at least one item to add it to the cart."
        }
    }

    private fun updateTotalAmount() {
        totalAmount.toMut().value = "INR ${itemPrice * quantity.value!!}"
    }

    fun addItemToCart() {
        d2.set(wRepo.getItemByIdInStallOfId(stallId, itemId)
                .subscribe { _item ->
                    wRepo.addEntryToCart(CartEntry(UUID.randomUUID().toString(), _item, quantity.value!!, true)).subscribe {
                        messages.toMut().postValue("Item added to cart.")
                    }
                })
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
        d2.clear()
    }
}