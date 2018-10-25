package com.example.nishant.fenrir.screens.mainapp.more.developers

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.util.toMut

class DevelopersViewModel : ViewModel() {

    val developers: LiveData<List<Developer>> = MutableLiveData()

    init {
        developers.toMut().value = listOf(
                Developer("Nishant Mahajan", "App Developer", ""),
                Developer("Hemanth V Alluri", "Backend Developer", ""),
                Developer("Divyam Goel", "Backend Developer", ""),
                Developer("Aaryan Budhiraja", "UX Designer", ""),
                Developer("Yash Bhagat", "UI Designer", "")
        )
    }
}