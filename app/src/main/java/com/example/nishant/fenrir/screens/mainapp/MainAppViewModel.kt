package com.example.nishant.fenrir.screens.mainapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.util.toMut

class MainAppViewModel(private val cRepo: CentralRepository) : ViewModel() {

    val shouldShowLogin: LiveData<Boolean> = MutableLiveData()
        get() {
            if(field.value == false) {
                return field
            }
            field.toMut().value = cRepo.getUserDetails().isEmpty.blockingGet()
            return field
        }

    init {
        shouldShowLogin.toMut().value = true
        cRepo.getUserDetails()
                .subscribe(
                        {
                            shouldShowLogin.toMut().value = false
                        },
                        {
                            shouldShowLogin.toMut().value = true
                        }
                )
    }
}