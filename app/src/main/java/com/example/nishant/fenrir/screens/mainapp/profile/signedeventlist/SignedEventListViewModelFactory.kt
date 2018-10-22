package com.example.nishant.fenrir.screens.mainapp.profile.signedeventlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.mainapp.MainAppRepository
import javax.inject.Inject

class SignedEventListViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var mainAppRepository: MainAppRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignedEventListViewModel(mainAppRepository) as T
    }
}