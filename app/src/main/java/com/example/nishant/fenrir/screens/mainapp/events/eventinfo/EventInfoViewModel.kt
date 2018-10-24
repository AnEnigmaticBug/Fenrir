package com.example.nishant.fenrir.screens.mainapp.events.eventinfo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.mainapp.MainAppRepository
import com.example.nishant.fenrir.util.notifications.NotificationScheduler
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable
import org.threeten.bp.LocalDateTime

class EventInfoViewModel(private val eRepo: MainAppRepository, private val notificationScheduler: NotificationScheduler, private val id: String) : ViewModel() {

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
            true  -> {
                notificationScheduler.cancelNotification(id.toInt())
                eRepo.undoSubscription(id).subscribe()
            }
            false -> {
                eRepo.getEventById(id).take(1).subscribe {
                    if(it.date != null && it.time != null) {
                        notificationScheduler.scheduleNotification(id.toInt(), LocalDateTime.of(it.date, it.time).minusMinutes(15), "Event reminder", "${it.name} is about to begin")
                    }
                }
                eRepo.makeSubscription(id).subscribe()
            }
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
        hasSubscribed = when(subscribed) {
            true  -> {
                remindButtonText.toMut().postValue("Un-Remind")
                true
            }
            false -> {
                remindButtonText.toMut().postValue("Remind Me")
                false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}