package com.example.nishant.fenrir.data.repository.mainapp

import com.example.nishant.fenrir.domain.mainapp.Comedian
import com.example.nishant.fenrir.domain.mainapp.Event
import com.example.nishant.fenrir.domain.mainapp.PayloadedNotification
import com.example.nishant.fenrir.domain.mainapp.SignedEvent
import io.reactivex.Completable
import io.reactivex.Flowable

interface MainAppRepository {

    fun getAllEvents(): Flowable<List<Event>>

    fun getEventById(id: String): Flowable<Event>

    fun makeSubscription(id: String): Completable

    fun undoSubscription(id: String): Completable

    fun getAllSignedEvents(): Flowable<List<SignedEvent>>

    fun isVotingEnabled(): Flowable<Boolean>

    fun getAllComedians(): Flowable<List<Comedian>>

    fun voteForComedian(name: String): Completable

    fun getAllPayloadedNotifications(): Flowable<List<PayloadedNotification>>
}