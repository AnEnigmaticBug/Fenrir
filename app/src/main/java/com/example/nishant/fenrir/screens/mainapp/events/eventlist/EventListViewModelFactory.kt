package com.example.nishant.fenrir.screens.mainapp.events.eventlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.data.repository.mainapp.EventRepository
import javax.inject.Inject

class EventListViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var centralRepository: CentralRepository
    @Inject
    lateinit var eventRepository: EventRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventListViewModel(centralRepository, eventRepository) as T
    }
}