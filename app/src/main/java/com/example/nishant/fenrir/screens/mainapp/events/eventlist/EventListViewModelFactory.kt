package com.example.nishant.fenrir.screens.mainapp.events.eventlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.data.repository.mainapp.MainAppRepository
import javax.inject.Inject

class EventListViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var centralRepository: CentralRepository
    @Inject
    lateinit var mainAppRepository: MainAppRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventListViewModel(centralRepository, mainAppRepository) as T
    }
}