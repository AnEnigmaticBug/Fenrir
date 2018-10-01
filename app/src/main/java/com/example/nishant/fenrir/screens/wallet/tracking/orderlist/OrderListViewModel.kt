package com.example.nishant.fenrir.screens.wallet.tracking.orderlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.TrackedEntry
import com.example.nishant.fenrir.screens.mainapp.events.eventlist.prettyString
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable
import org.threeten.bp.LocalDateTime


class OrderListViewModel(wRepo: WalletRepository) : ViewModel() {

    val orders: LiveData<List<RawOrder>> = MutableLiveData()

    private val d1 = CompositeDisposable()

    init {
        d1.set(wRepo.getAllTrackedEntries()
                .subscribe {
                    orders.toMut().postValue(it.toRawOrderList())
                })
    }

    private fun List<TrackedEntry>.toRawOrderList(): List<RawOrder> {
        val x = this.distinctBy { it.orderId }.sortedByDescending { it.orderId }
        return x.mapIndexed { i, it ->
            RawOrder(it.orderId, "Order Number ${x.size - i}", LocalDateTime.of(it.orderDate, it.orderTime).prettyString())
        }
    }

    private fun LocalDateTime.prettyString(): String {
        val date = "${this.dayOfMonth}-${this.monthValue}-${this.year % 2000}"
        val time = this.toLocalTime().prettyString()
        return "Ordered on $date at $time"
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}