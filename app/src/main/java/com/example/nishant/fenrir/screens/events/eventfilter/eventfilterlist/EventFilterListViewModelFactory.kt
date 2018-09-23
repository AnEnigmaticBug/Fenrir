package com.example.nishant.fenrir.screens.events.eventfilter.eventfilterlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.CentralRepository
import javax.inject.Inject

class EventFilterListViewModelFactory(private val type: FilterType) : ViewModelProvider.Factory {

    @Inject
    lateinit var centralRepository: CentralRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventFilterListViewModel(centralRepository, type) as T
    }
}