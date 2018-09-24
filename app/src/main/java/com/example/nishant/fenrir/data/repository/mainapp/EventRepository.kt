package com.example.nishant.fenrir.data.repository.mainapp

import com.example.nishant.fenrir.domain.mainapp.Event
import io.reactivex.Completable
import io.reactivex.Flowable

interface EventRepository {

    fun getAllEvents(): Flowable<List<Event>>

    fun getEventById(id: String): Flowable<Event>

    fun makeSubscription(id: String): Completable

    fun undoSubscription(id: String): Completable
}