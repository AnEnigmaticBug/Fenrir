package com.example.nishant.fenrir.screens.mainapp.more.sponsors

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.util.toMut

class SponsorsViewModel : ViewModel() {

    val sponsors: LiveData<List<Sponsor>> = MutableLiveData()

    init {
        sponsors.toMut().value = listOf(
                Sponsor("One Plus", "Title Sponsor", ""),
                Sponsor("Comedy Central", "Associate Title Sponsor", ""),
                Sponsor("VH1", "Associate Title Sponsor", ""),
                Sponsor("Pepsi", "Official Beverage Partner", "")
        )
    }
}