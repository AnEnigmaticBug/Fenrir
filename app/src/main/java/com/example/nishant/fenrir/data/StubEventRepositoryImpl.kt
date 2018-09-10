package com.example.nishant.fenrir.data

import com.example.nishant.fenrir.domain.Category
import com.example.nishant.fenrir.domain.Event
import com.example.nishant.fenrir.domain.Venue
import com.example.nishant.fenrir.util.Constants
import io.reactivex.Completable
import io.reactivex.Flowable
import org.threeten.bp.LocalTime

class StubEventRepositoryImpl : EventRepository {

    private val events = mutableListOf(
            Event("01", "Event Name 1", "A1", "R1", Venue.Stalls, Category.Dance, Constants.festDates[0], LocalTime.of(18, 0), 60, false),
            Event("02", "Event Name 2", "A2", "R2", Venue.MLawns, Category.Drama, Constants.festDates[0], LocalTime.of(20, 30), 90, true),
            Event("03", "Event Name 3", "A3", "R3", Venue.Audi, Category.Music, Constants.festDates[0], LocalTime.of(8, 0), 60, false),
            Event("04", "Event Name 4", "A4", "R4", Venue.SACHall, Category.Films, Constants.festDates[0], LocalTime.of(23, 0), 30, false),
            Event("05", "Event Name 5", "A5", "R5", Venue.Rotunda, Category.Dance, Constants.festDates[0], LocalTime.of(21, 45), 45, false),
            Event("06", "Event Name 6", "A6", "R6", Venue.Stalls, Category.Drama, Constants.festDates[1], LocalTime.of(13, 0), 60, false),
            Event("07", "Event Name 7", "A7", "R7", Venue.MLawns, Category.Music, Constants.festDates[1], LocalTime.of(17, 30), 180, false),
            Event("08", "Event Name 8", "A8", "R8", Venue.Audi, Category.Films, Constants.festDates[1], LocalTime.of(18, 0), 90, true)
    )

    override fun getAllEvents(): Flowable<List<Event>> {
        return Flowable.just(events)
    }

    override fun getEventById(id: String): Flowable<Event> {
        val e = events.find { it.id == id }!!
        return Flowable.just(e)
    }

    override fun makeSubscription(id: String): Completable {
        return Completable.fromAction {
            val i = events.indexOfFirst { it.id == id }
            events[i] = events[i].copy(subscribed = true)
        }
    }

    override fun undoSubscription(id: String): Completable {
        return Completable.fromAction {
            val i = events.indexOfFirst { it.id == id }
            events[i] = events[i].copy(subscribed = false)
        }
    }
}