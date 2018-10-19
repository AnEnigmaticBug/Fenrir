package com.example.nishant.fenrir.data.firestore.wallet

import com.example.nishant.fenrir.data.repository.CentralRepository
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class FireAccountant(cRepo: CentralRepository) {

    private val db = FirebaseFirestore.getInstance()
    private val subject = BehaviorSubject.create<Int>()

    init {
        cRepo.getUserDetails()
                .subscribe {
                    db.collection("User #${it.id}")
                            .addSnapshotListener { s, _ ->
                                s?.documents?.first { it.id == "Balance" }
                                        ?.also {
                                            val total = it.getLong("cash")!! + it.getLong("swd")!! + it.getLong("transfers")!!
                                            subject.onNext(total.toInt())
                                        }
                            }
                }
    }

    fun getBalance(): Flowable<Int> = subject.toFlowable(BackpressureStrategy.LATEST)
}