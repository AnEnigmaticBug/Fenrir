package com.example.nishant.fenrir.screens.events.eventlist

import com.example.nishant.fenrir.domain.Event

data class RawEvent(val id: String, val name: String, val venue: String, val time: String, val subscribed: Boolean) {

    companion object {

        fun fromEvent(event: Event): RawEvent {
            return RawEvent(
                    event.id,
                    event.name,
                    event.venue.prettyString(),
                    event.time.prettyString(),
                    event.subscribed)
        }
    }
}