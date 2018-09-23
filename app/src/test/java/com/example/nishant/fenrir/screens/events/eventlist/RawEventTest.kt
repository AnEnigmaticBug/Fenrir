package com.example.nishant.fenrir.screens.events.eventlist

import com.example.nishant.fenrir.domain.Category
import com.example.nishant.fenrir.domain.Event
import com.example.nishant.fenrir.domain.Venue
import com.example.nishant.fenrir.util.Constants
import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.LocalTime

class RawEventTest {

    @Test
    fun test_FromEvent() {
        val event = Event("13", "EV", "AB", "RU", Venue.SACHall, Category.Films, Constants.festDates[2], LocalTime.of(19, 0), 90, false)
        val rawEvent = RawEvent("13", "EV", "SAC Hall", "07:00 PM", false)
        assertEquals(rawEvent, RawEvent.fromEvent(event))
    }
}