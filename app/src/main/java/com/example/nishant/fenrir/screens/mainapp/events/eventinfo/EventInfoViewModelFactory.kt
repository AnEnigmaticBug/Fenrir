package com.example.nishant.fenrir.screens.mainapp.events.eventinfo

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.mainapp.MainAppRepository
import com.example.nishant.fenrir.util.notifications.NotificationScheduler
import javax.inject.Inject

class EventInfoViewModelFactory(private val eventId: String) : ViewModelProvider.Factory {

    @Inject
    lateinit var mainAppRepository: MainAppRepository

    @Inject
    lateinit var notificationScheduler: NotificationScheduler

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventInfoViewModel(mainAppRepository, notificationScheduler, eventId) as T
    }
}