package com.example.nishant.fenrir.data.firestore.mainapp

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.BehaviorSubject

class N2OManager {

    private val db = FirebaseFirestore.getInstance()

    private val comediansSubject = BehaviorSubject.create<List<RawComedian>>()
    private val statusSubject = BehaviorSubject.create<Boolean>()

    init {
        db.collection("N2O Voting")
                .document("Details")
                .addSnapshotListener { it, _ ->
                    if(it != null) {
                        val comedians = ArrayList<RawComedian>()
                        (it.get("comedians") as ArrayList<Map<String, Object>>).forEach {
                            comedians.add(RawComedian(it.get("name") as String, it.get("profilePicURL") as String))
                        }
                        comediansSubject.onNext(comedians)

                        statusSubject.onNext(it.getBoolean("isVotingEnabled")!!)
                    }

                }
    }

    fun getComedians() = comediansSubject.toFlowable(BackpressureStrategy.LATEST)

    fun getEnabledStatus() = statusSubject.toFlowable(BackpressureStrategy.LATEST)

    fun voteForComedian(name: String) {
        db.collection("N2O Voting")
                .add(mapOf("name" to name))
    }
}