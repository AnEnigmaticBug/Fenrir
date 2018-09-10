package com.example.nishant.fenrir.events.eventinfo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.EventRepository
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable

class EventInfoViewModel(private val eRepo: EventRepository, private val id: String) : ViewModel() {

    val rawEvent: LiveData<RawEvent> = MutableLiveData()
    val remindButtonText: LiveData<String> = MutableLiveData()
    val toggleButtonText: LiveData<String> = MutableLiveData()


    private var hasSubscribed = false
    private var isInAboutMode = true

    private var d1 = CompositeDisposable()

    init {
        setAboutMode()
    }

    fun toggleSubscription() {
        when(hasSubscribed) {
            true  -> eRepo.undoSubscription(id).subscribe()
            false -> eRepo.makeSubscription(id).subscribe()
        }
    }

    fun toggleMainContents() {
        when(isInAboutMode) {
            true  -> setRulesMode()
            false -> setAboutMode()
        }
    }

    private fun setAboutMode() {
        isInAboutMode = true
        toggleButtonText.toMut().value = "Rules"
        d1.set(eRepo.getEventById(id)
                .subscribe { _event ->
                    rawEvent.toMut().postValue(RawEvent.fromEventOnlyAboutInfo(_event))
                    updateSubscriptionStuff(_event.subscribed)
                })
    }

    private fun setRulesMode() {
        isInAboutMode = false
        toggleButtonText.toMut().value = "About"
        d1.set(eRepo.getEventById(id)
                .subscribe { _event ->
                    rawEvent.toMut().postValue(RawEvent.fromEventOnlyRulesInfo(_event))
                    updateSubscriptionStuff(_event.subscribed)
                })
    }

    private fun updateSubscriptionStuff(subscribed: Boolean) {
        when(subscribed) {
            true  -> {
                remindButtonText.toMut().postValue("Un-Remind")
                hasSubscribed = true
            }
            false -> {
                remindButtonText.toMut().postValue("Remind Me")
                hasSubscribed = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}