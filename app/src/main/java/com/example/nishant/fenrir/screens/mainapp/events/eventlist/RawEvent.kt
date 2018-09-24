package com.example.nishant.fenrir.screens.mainapp.events.eventlist

import com.example.nishant.fenrir.domain.mainapp.Event

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