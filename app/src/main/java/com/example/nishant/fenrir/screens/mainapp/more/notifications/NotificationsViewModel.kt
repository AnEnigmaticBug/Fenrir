package com.example.nishant.fenrir.screens.mainapp.more.notifications

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nishant.fenrir.data.repository.mainapp.MainAppRepository
import com.example.nishant.fenrir.util.prettyString
import com.example.nishant.fenrir.util.set
import com.example.nishant.fenrir.util.toMut
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime

class NotificationsViewModel(mRepo: MainAppRepository) : ViewModel() {

    val payloadedNotifications: LiveData<List<RawPayloadedNotification>> = MutableLiveData()

    private val d1 = CompositeDisposable()

    init {
        d1.set(mRepo.getAllPayloadedNotifications()
                .subscribeOn(Schedulers.io())
                .subscribe {
                    payloadedNotifications.toMut().postValue(
                            it.map { RawPayloadedNotification(it.id, it.title, it.datetime.prettyString(), it.body) }
                    )
                })
    }

    private fun LocalDateTime.prettyString(): String {
        return "Sent on ${this.toLocalDate().prettyString()} at ${this.toLocalTime().prettyString()}"
    }

    override fun onCleared() {
        super.onCleared()
        d1.clear()
    }
}