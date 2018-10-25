package com.example.nishant.fenrir.screens.wallet.tracking.trackorder

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.domain.wallet.NotifyOTPResult
import com.example.nishant.fenrir.domain.wallet.Stall
import com.example.nishant.fenrir.domain.wallet.TrackedEntry
import com.example.nishant.fenrir.domain.wallet.TrackingStatus
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class TrackOrderViewModel(private val wRepo: WalletRepository, orderId: String) : ViewModel() {

    val trackedEntries: LiveData<List<TrackingScreenRow>> = MutableLiveData()
    val status: LiveData<NotifyOTPStatus> = MutableLiveData()

    private val d1 = CompositeDisposable()
    private val d2 = CompositeDisposable()

    init {
        status.toMut().value = NotifyOTPStatus.Idle
        d1.set(wRepo.getAllStalls()
                .subscribe { _stalls ->
                    d2.set(wRepo.getAllTrackedEntries()
                            .subscribe { _te ->
                                val trackingScreenRows = ArrayList<TrackingScreenRow>()
                                _te.filter { it.orderId == orderId }.groupBy { it.item.stallId }.map { it.value }.forEach { _ste ->
                                    val stallName = _stalls.first { it.id == _ste.first().item.stallId }.name
                                    val otp = when(_ste.first().status) {
                                        TrackingStatus.Ready -> _ste.first().otp
                                        else                 -> null
                                    }
                                    trackingScreenRows.add(TrackingScreenRow.Stall(_ste.first().id, stallName, otp, _ste.first().otpShown))
                                    trackingScreenRows.addAll(_ste.map { it.toEntry(_stalls) })
                                    Unit
                                }
                                trackedEntries.toMut().postValue(
                                        trackingScreenRows
                                )
                            })
                })
    }

    private fun TrackedEntry.toEntry(stalls: List<Stall>): TrackingScreenRow {
        val stall = stalls.first { it.id == this.item.stallId }

        val priceAndQuantity = "INR ${this.item.price} x${this.quantity}"

        return TrackingScreenRow.Entry(this.id, this.item.name, priceAndQuantity, this.status)
    }

    fun notifyOTPShown(entryId: String) {
        status.toMut().value = NotifyOTPStatus.InProgress
        wRepo.notifyOTPShown(entryId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            status.toMut().value = when(result) {
                                is NotifyOTPResult.Success -> {
                                    NotifyOTPStatus.Success
                                }
                                is NotifyOTPResult.Failure -> {
                                    when(result) {
                                        is NotifyOTPResult.Failure.NoInternet -> NotifyOTPStatus.Failure("No internet found")
                                        is NotifyOTPResult.Failure.ServerBusy -> NotifyOTPStatus.Failure("Couldn't show OTP")
                                    }
                                }
                            }
                            status.toMut().value = NotifyOTPStatus.Idle
                        },
                        {
                            status.toMut().value = NotifyOTPStatus.Failure("Error! Please try again")
                        }
                )
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
        d2.clear()
    }
}