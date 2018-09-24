package com.example.nishant.fenrir.domain

import com.example.nishant.fenrir.domain.mainapp.Category
import com.example.nishant.fenrir.domain.mainapp.Event
import com.example.nishant.fenrir.domain.mainapp.EventFilter
import com.example.nishant.fenrir.domain.mainapp.Venue
import com.example.nishant.fenrir.util.Constants
import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class EventFilterTest {

    private val event = Event("13", "E1", "A1", "R1", Venue.MLawns, Category.Films, Constants.festDates[1], LocalTime.of(22, 30), 60, false)

    @Test
    fun test_IsSatisfiedByEvent_WhereCategoryIsBlocking() {
        val filter = EventFilter(Category.Music, null, false, false)
        assertFalse(filter.isSatisfiedByEvent(event))
    }

    @Test
    fun test_IsSatisfiedByEvent_WhereCategoryIsAllowing() {
        val filter = EventFilter(Category.Films, null, false, false)
        assertTrue(filter.isSatisfiedByEvent(event))
    }

    @Test
    fun test_IsSatisfiedByEvent_WhereVenueIsBlocking() {
        val filter = EventFilter(null, Venue.Stalls, false, false)
        assertFalse(filter.isSatisfiedByEvent(event))
    }

    @Test
    fun test_IsSatisfiedByEvent_WhereVenueIsAllowing() {
        val filter = EventFilter(null, Venue.MLawns, false, false)
        assertTrue(filter.isSatisfiedByEvent(event))
    }

    @Test
    fun test_IsSatisfiedByEvent_WhereEventIsNotOngoing() {
        val filter = EventFilter(null, null, true, false)
        assertFalse(filter.isSatisfiedByEvent(event.copy(date = LocalDate.now(), time = LocalTime.now().plusMinutes(70))))
    }

    @Test
    fun test_IsSatisfiedByEvent_WhereEventIsOngoing() {
        val filter = EventFilter(null, null, true, false)
        assertTrue(filter.isSatisfiedByEvent(event.copy(date = LocalDate.now(), time = LocalTime.now().minusMinutes(30))))
    }

    @Test
    fun test_IsSatisfiedByEvent_WhereEventIsNotSubscribed() {
        val filter = EventFilter(null, null, false, true)
        assertFalse(filter.isSatisfiedByEvent(event))
    }

    @Test
    fun test_IsSatisfiedByEvent_WhereEventIsSubscribed() {
        val filter = EventFilter(null, null, false, true)
        assertTrue(filter.isSatisfiedByEvent(event.copy(subscribed = true)))
    }
}