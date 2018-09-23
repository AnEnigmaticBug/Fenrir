package com.example.nishant.fenrir.screens.events.eventfilter.eventfiltermenu

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.CentralRepository
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable

class EventFilterMenuViewModel(private val cRepo: CentralRepository) : ViewModel() {

    val showOnlyOngoing: LiveData<Boolean> = MutableLiveData()
    val showOnlySubscribed: LiveData<Boolean> = MutableLiveData()


    private val d1 = CompositeDisposable()
    private val d2 = CompositeDisposable()
    private val d3 = CompositeDisposable()

    init {
        d1.set(cRepo.getUserPreferences()
                .subscribe { _prefs ->
                    showOnlyOngoing.toMut().postValue(_prefs.eventFilter.showOnlyOngoing)
                    showOnlySubscribed.toMut().postValue(_prefs.eventFilter.showOnlySubscribed)
                })
    }

    fun toggleShowOnlyOngoingFilter() {
        d2.set(cRepo.getUserPreferences()
                .take(1)
                .subscribe { _prefs ->
                    val eventFilter = _prefs.eventFilter.copy(showOnlyOngoing = !_prefs.eventFilter.showOnlyOngoing)
                    cRepo.setUserPreferences(_prefs.copy(eventFilter = eventFilter))
                            .subscribe()
                })
    }

    fun toggleShowOnlySubscribedFilter() {
        d3.set(cRepo.getUserPreferences()
                .take(1)
                .subscribe { _prefs ->
                    val eventFilter = _prefs.eventFilter.copy(showOnlySubscribed = !_prefs.eventFilter.showOnlySubscribed)
                    cRepo.setUserPreferences(_prefs.copy(eventFilter = eventFilter))
                            .subscribe()
                })
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
        d2.clear()
        d3.clear()
    }
}