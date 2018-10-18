package com.example.nishant.fenrir.screens.mainapp.profile

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.CentralRepository
import javax.inject.Inject

class ProfileViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var centralRepository: CentralRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(centralRepository) as T
    }
}