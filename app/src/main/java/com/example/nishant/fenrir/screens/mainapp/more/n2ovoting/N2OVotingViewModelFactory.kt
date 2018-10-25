package com.example.nishant.fenrir.screens.mainapp.more.n2ovoting

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.data.repository.mainapp.MainAppRepository
import javax.inject.Inject

class N2OVotingViewModelFactory : ViewModelProvider.Factory {

    @Inject
    lateinit var mainAppRepository: MainAppRepository

    init {
        FenrirApp.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return N2OVotingViewModel(mainAppRepository) as T
    }
}