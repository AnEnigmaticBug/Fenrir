package com.example.nishant.fenrir.screens.wallet.tracking.trackorder

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.Stall
import com.example.nishant.fenrir.domain.wallet.TrackedEntry
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable

class TrackOrderViewModel(wRepo: WalletRepository, orderId: String) : ViewModel() {

    val trackedEntries: LiveData<List<RawTrackedEntry>> = MutableLiveData()

    private val d1 = CompositeDisposable()
    private val d2 = CompositeDisposable()

    init {
        d1.set(wRepo.getAllStalls()
                .subscribe { _stalls ->
                    d2.set(wRepo.getAllTrackedEntries()
                            .subscribe {
                                trackedEntries.toMut().postValue(
                                        it.filter { it.orderId == orderId }.map { it.toRawTrackedEntry(_stalls) }
                                )
                            })
                })
    }

    private fun TrackedEntry.toRawTrackedEntry(stalls: List<Stall>): RawTrackedEntry {
        val stall = stalls.first { it.id == this.item.stallId }

        val priceAndQuantity = "INR ${this.item.price} x${this.quantity}"

        return RawTrackedEntry(this.id, this.item.name, stall.name, priceAndQuantity, this.status)
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
        d2.clear()
    }
}