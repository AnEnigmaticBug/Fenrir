package com.example.nishant.fenrir.screens.mainapp.events.eventinfo

import com.example.nishant.fenrir.domain.mainapp.Event
import com.example.nishant.fenrir.screens.mainapp.events.eventlist.prettyString
import com.example.nishant.fenrir.util.Constants
import org.threeten.bp.LocalDate

data class RawEvent(val id: String, val name: String, val venue: String, val timeAndDay: String, val mainContent: String) {

    companion object {

        fun fromEventOnlyAboutInfo(event: Event): RawEvent {
            return RawEvent(
                    event.id,
                    event.name,
                    event.venue.prettyString(),
                    "${event.time.prettyString()}, ${event.date.getFestDayRepresentation()}",
                    event.about
            )
        }

        fun fromEventOnlyRulesInfo(event: Event): RawEvent {
            return RawEvent(
                    event.id,
                    event.name,
                    event.venue.prettyString(),
                    "${event.time.prettyString()}, ${event.date.getFestDayRepresentation()}",
                    event.rules
            )
        }

        private fun LocalDate?.getFestDayRepresentation() = when(this) {
            Constants.festDates[0] -> "Day 1"
            Constants.festDates[1] -> "Day 2"
            Constants.festDates[2] -> "Day 3"
            Constants.festDates[3] -> "Day 4"
            Constants.festDates[4] -> "Day 5"
            null                   -> "TBA"
            else                   -> throw IllegalArgumentException("Event doesn't occur during Oasis.")
        }
    }
}