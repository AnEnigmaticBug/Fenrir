package com.example.nishant.fenrir.screens.wallet.stalls

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable

class StallsViewModel(wRepo: WalletRepository) : ViewModel() {

    val rawStalls: LiveData<List<RawStall>> = MutableLiveData()

    private val d1 = CompositeDisposable()

    init {
        d1.set(wRepo.getAllStalls()
                .subscribe { _stalls ->
                    rawStalls.toMut().postValue(_stalls.map { RawStall.fromStall(it) })
                })
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}