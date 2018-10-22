package com.example.nishant.fenrir.data.repository.mainapp

import android.annotation.SuppressLint
import android.util.Log
import com.example.nishant.fenrir.data.firestore.mainapp.FirestoreEventDatabase
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.data.retrofit.NetworkWatcher
import com.example.nishant.fenrir.data.retrofit.mainapp.MainAppService
import com.example.nishant.fenrir.data.room.mainapp.MainAppDao
import com.example.nishant.fenrir.data.room.mainapp.RawEvent
import com.example.nishant.fenrir.data.room.mainapp.RawSignedEvent
import com.example.nishant.fenrir.domain.mainapp.Event
import com.example.nishant.fenrir.domain.mainapp.SignedEvent
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class MainAppRepositoryImpl(fsDb: FirestoreEventDatabase, private val cRepo: CentralRepository, private val networkWatcher: NetworkWatcher, private val mainAppService: MainAppService, private val mainAppDao: MainAppDao) : MainAppRepository {

    init {
        val d = fsDb.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .take(1)
                .subscribe {
                    it.forEach {
                        if (mainAppDao.eventExists(it.id)) {
                            getEventById(it.id)
                                    .take(1)
                                    .subscribe { dbEvent ->
                                        mainAppDao.updateEvent(RawEvent(
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
                            mainAppDao.insertEvent(it)
                        }
                    }
                }
    }

    override fun getAllEvents(): Flowable<List<Event>> {
        return mainAppDao.getAllEvents().map { it.map { it.toEvent() } }.subscribeOn(Schedulers.io())
    }

    override fun getEventById(id: String): Flowable<Event> {
        return mainAppDao.getEventById(id).map { it.toEvent() }.subscribeOn(Schedulers.io())
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
        mainAppDao.getEventById(id)
                .take(1)
                .map { it.copy(subscribed = status) }
                .subscribe { mainAppDao.updateEvent(it) }
    }

    override fun getAllSignedEvents(): Flowable<List<SignedEvent>> {
        if(networkWatcher.checkIfConnectedToInternet()) {
            cRepo.getUserDetails()
                    .subscribe(
                            { _userDetails ->
                                val requestBody = JsonObject().also {
                                    it.addProperty("qr_code", _userDetails.qrCodeContent)
                                }
                                Log.d("CANDY", requestBody.toString())
                                mainAppService.getSignedEvents(_userDetails.jwtToken, requestBody)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe { _response ->
                                            if(_response.isSuccessful) {
                                                Log.d("CANDY", "CANDY")
                                                val body = _response.body()!!
                                                if(!body.events.isEmpty()) {
                                                    body.events.forEach {
                                                        mainAppDao.insertSignedEvent(RawSignedEvent(it.id, it.name, it.numberOfTickets))
                                                    }
                                                }
                                            }
                                        }
                            },
                            {

                            }
                    )
        }
        return mainAppDao.getAllSignedEvents().map { it.map { SignedEvent(it.id, it.name, it.numberOfTickets) } }
    }
}