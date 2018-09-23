package com.example.nishant.fenrir.screens.events.eventinfo

import com.example.nishant.fenrir.domain.Category
import com.example.nishant.fenrir.domain.Event
import com.example.nishant.fenrir.domain.Venue
import com.example.nishant.fenrir.util.Constants
import org.junit.Assert
import org.junit.Test
import org.threeten.bp.LocalTime

class RawEventTest {

    @Test
    fun test_FromEventOnlyAboutInfo() {
        val event = Event("13", "EV", "AB", "RU", Venue.SACHall, Category.Films, Constants.festDates[2], LocalTime.of(19, 0), 90, false)
        val rawEvent = RawEvent("13", "EV", "SAC Hall", "07:00 PM, Day 3", "AB")
        Assert.assertEquals(rawEvent, RawEvent.fromEventOnlyAboutInfo(event))
    }

    @Test
    fun test_FromEventOnlyRulesInfo() {
        val event = Event("34", "EV", "AB", "RU", Venue.SACHall, Category.Films, Constants.festDates[1], LocalTime.of(11, 15), 60, true)
        val rawEvent = RawEvent("34", "EV", "SAC Hall", "11:15 AM, Day 2", "RU")
        Assert.assertEquals(rawEvent, RawEvent.fromEventOnlyRulesInfo(event))
    }
}