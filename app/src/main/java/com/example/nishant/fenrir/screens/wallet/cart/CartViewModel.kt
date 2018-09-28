package com.example.nishant.fenrir.screens.wallet.cart

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.BuyAttemptResult
import com.example.nishant.fenrir.domain.wallet.CartEntry
import com.example.nishant.fenrir.domain.wallet.Stall
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CartViewModel(private val wRepo: WalletRepository) : ViewModel() {

    val rawOrders: LiveData<List<RawCartEntry>> = MutableLiveData()
    val totalCost: LiveData<String> = MutableLiveData()
    val buyStatus: LiveData<BuyAttemptStatus> = MutableLiveData()

    private val d1 = CompositeDisposable()
    private val d2 = CompositeDisposable()
    private val d3 = CompositeDisposable()

    init {
        buyStatus.toMut().value = BuyAttemptStatus.Idle
        d1.set(wRepo.getAllStalls()
                .subscribe { _stalls ->
                    d2.set(wRepo.getAllEntriesInCart()
                            .subscribe { _orders ->
                                rawOrders.toMut().postValue(
                                        _orders.map { it.rawForm(_stalls.first { _stall -> _stall.id == it.item.stallId }) }
                                )
                                totalCost.toMut().postValue(
                                        "Total Price: INR ${calculateTotalCost(_orders)}"
                                )
                            })
                })
    }

    fun CartEntry.rawForm(stall: Stall) = RawCartEntry(id, item.name, stall.name,"INR ${item.price} x$quantity", isValid)

    private fun calculateTotalCost(orders: List<CartEntry>): Int {
        return when(orders.size) {
            0    -> 0
            1    -> orders.first().item.price * orders.first().quantity
            else -> orders.map { it.item.price * it.quantity }.reduce { acc, i -> acc + i }
        }
    }

    fun onOrderRemoved(orderId: String) {
        wRepo.removeEntryFromCartById(orderId).subscribe()
    }

    fun onPayButtonClicked() {
        buyStatus.toMut().value = BuyAttemptStatus.InProgress
        d3.set(wRepo.orderAllEntriesInCart()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _attempt ->
                    when(_attempt) {
                        is BuyAttemptResult.Success -> {
                            wRepo.getAllEntriesInCart()
                                    .take(1)
                                    .subscribe {
                                        it.forEach { wRepo.removeEntryFromCartById(it.id).subscribe() }
                                    }
                            buyStatus.toMut().value = BuyAttemptStatus.Success
                        }
                        is BuyAttemptResult.Failure -> {
                            when(_attempt) {
                                is BuyAttemptResult.Failure.NoInternet -> {
                                    buyStatus.toMut().value = BuyAttemptStatus.Failure("No internet found")
                                }
                                is BuyAttemptResult.Failure.VacantCart -> {
                                    buyStatus.toMut().value = BuyAttemptStatus.Failure("The cart is empty")
                                }
                                is BuyAttemptResult.Failure.LessBudget -> {
                                    buyStatus.toMut().value = BuyAttemptStatus.Failure("Not enough budget")
                                }
                                is BuyAttemptResult.Failure.OutOfStock -> {
                                    _attempt.itemIds.forEach { wRepo.invalidateEntryWithItemId(it).subscribeOn(Schedulers.io()).subscribe() }
                                    buyStatus.toMut().value = BuyAttemptStatus.Failure("Some items are out of stock. Please remove them")
                                }
                            }
                        }
                    }
                    buyStatus.toMut().value = BuyAttemptStatus.Idle
                })
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
        d2.clear()
        d3.clear()
    }
}