package com.example.nishant.fenrir.data

import android.annotation.SuppressLint
import com.example.nishant.fenrir.data.firestore.FirestoreDatabase
import com.example.nishant.fenrir.data.room.EventDao
import com.example.nishant.fenrir.data.room.RawEvent
import com.example.nishant.fenrir.domain.Event
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class FirestoreEventRepository(fsDb: FirestoreDatabase, private val eventDao: EventDao) : EventRepository {

    init {
        val d = fsDb.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .take(1)
                .subscribe {
                    it.forEach {
                        if (eventDao.eventExists(it.id)) {
                            getEventById(it.id)
                                    .take(1)
                                    .subscribe { dbEvent ->
                                        eventDao.update(RawEvent(
                                                it.id,
                                                it.name,
                                                it.about,
                                                it.rules,
                                                it.venue,
                                                it.category,
                                                it.date,
                                                it.time,
                                                it.duration,
                                                dbEvent.subscribed
                                        ))
                                    }
                        } else {
                            eventDao.insert(it)
                        }
                    }
                }
    }

    override fun getAllEvents(): Flowable<List<Event>> {
        return eventDao.getAllEvents().map { it.map { it.toEvent() } }.subscribeOn(Schedulers.io())
    }

    override fun getEventById(id: String): Flowable<Event> {
        return eventDao.getEventById(id).map { it.toEvent() }.subscribeOn(Schedulers.io())
    }

    override fun makeSubscription(id: String): Completable {
        return Completable.fromAction {
            setSubscription(id, true)
        }.subscribeOn(Schedulers.io())
    }

    override fun undoSubscription(id: String): Completable {
        return Completable.fromAction {
            setSubscription(id, false)
        }.subscribeOn(Schedulers.io())
    }

    @SuppressLint("CheckResult")
    private fun setSubscription(id: String, status: Boolean) {
        eventDao.getEventById(id)
                .take(1)
                .map { it.copy(subscribed = status) }
                .subscribe { eventDao.update(it) }
    }
}