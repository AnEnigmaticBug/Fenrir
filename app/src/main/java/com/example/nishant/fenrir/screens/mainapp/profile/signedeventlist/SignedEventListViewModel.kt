package com.example.nishant.fenrir.screens.mainapp.profile.signedeventlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.mainapp.MainAppRepository
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignedEventListViewModel(mRepo: MainAppRepository) : ViewModel() {

    val events: LiveData<List<RawSignedEvent>> = MutableLiveData()

    private val d1 = CompositeDisposable()

    init {
        d1.set(mRepo.getAllSignedEvents()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            events.toMut().postValue(it.map { RawSignedEvent(it.name, it.numberOfTickets) })
                        },
                        {

                        }
                ))
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}