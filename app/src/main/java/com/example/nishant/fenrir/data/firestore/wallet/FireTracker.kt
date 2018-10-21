package com.example.nishant.fenrir.data.firestore.wallet

import android.util.Log
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.domain.wallet.TrackingStatus
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import kotlin.collections.ArrayList

class FireTracker(cRepo: CentralRepository) {

    private val db = FirebaseFirestore.getInstance()
    private val subject = BehaviorSubject.create<List<RawTrackedEntry>>()

    init {
        cRepo.getUserDetails()
                .subscribe {
                    db.collection("User #${it.id}")
                            .addSnapshotListener { s, _ ->
                                val trackedEntries = arrayListOf<RawTrackedEntry>()
                                s?.documents?.filter { it.id.contains("OrderFragment") }?.forEach {
                                    it.toRawTrackedEntries().forEach {
                                        trackedEntries.add(it)
                                    }
                                }
                                Log.d("CANDY", trackedEntries.map { it.status }.joinToString("<|>"))
                                subject.onNext(trackedEntries)
                            }
                }
    }

    fun getTrackedEntries(): Flowable<List<RawTrackedEntry>> = subject.toFlowable(BackpressureStrategy.LATEST)

    private fun DocumentSnapshot.toRawTrackedEntries(): List<RawTrackedEntry> {
        val trackedEntries = arrayListOf<RawTrackedEntry>()
        val datetime = Instant.ofEpochSecond(getTimestamp("timestamp")!!.seconds).atZone(ZoneId.systemDefault()).toLocalDateTime()
        (get("items_list") as ArrayList<Map<String, Object>>).forEach {
            trackedEntries.add(RawTrackedEntry(
                    get("order").toString(),
                    it["id"]!!.toString(),
                    get("stall_id").toString(),
                    it["qty"]!!.toString().toInt(),
                    TrackingStatus.valueOf(getString("status")!!),
                    getLong("otp")!!.toInt(),
                    datetime.toLocalDate(),
                    datetime.toLocalTime()
            ))
        }
        return trackedEntries
    }
}