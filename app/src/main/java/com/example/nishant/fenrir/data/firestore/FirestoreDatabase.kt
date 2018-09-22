package com.example.nishant.fenrir.data.firestore

import android.text.Html
import com.example.nishant.fenrir.data.room.RawEvent
import com.example.nishant.fenrir.domain.Category
import com.example.nishant.fenrir.domain.Venue
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class FirestoreEventDatabase {

    private val db = FirebaseFirestore.getInstance()
    private val subject = BehaviorSubject.create<List<RawEvent>>()

    init {
        db.collection("Events").orderBy("time")
                .addSnapshotListener { s, _ ->
                    val events = arrayListOf<RawEvent>()
                    s?.documents?.forEach {
                        events.add(it.toRawEvent())
                    }
                    subject.onNext(events)
                }
    }

    fun getEvents(): Flowable<List<RawEvent>> = subject.toFlowable(BackpressureStrategy.LATEST)

    private fun DocumentSnapshot.toRawEvent(): RawEvent {
        return RawEvent(
                getLong("id")!!.toString(),
                getString("name")!!,
                Html.fromHtml(getString("content")!!).toString(),
                Html.fromHtml(getString("rules")!!).toString(),
                when(getString("venue")!!) {
                    "TBA" -> null
                    else  -> Venue.valueOf(getString("venue")!!)
                },
                Category.valueOf(getString("category")!!),
                when(getString("date")!!) {
                    "TBA" -> null
                    else  -> LocalDate.parse(getString("date")!!)
                },
                when(getString("time")!!) {
                    "TBA" -> null
                    else  -> LocalTime.parse(getString("time")!!)
                },
                getLong("duration")!!.toInt(),
                false
        )
    }
}