package com.example.nishant.fenrir.screens.mainapp.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.util.toMut

class ProfileViewModel(cRepo: CentralRepository) : ViewModel() {

    val userDetails: LiveData<RawUserDetails> = MutableLiveData()

    init {
        cRepo.getUserDetails()
                .subscribe {
                    userDetails.toMut().value = RawUserDetails(it.name, it.profilePicURL, it.qrCodeContent)
                }
    }
}