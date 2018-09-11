package com.example.nishant.fenrir.events.eventinfo

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.EventRepository
import javax.inject.Inject

class EventInfoViewModelFactory(private val eventId: String) : ViewModelProvider.Factory {

    @Inject
    lateinit var eventRepository: EventRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventInfoViewModel(eventRepository, eventId) as T
    }
}