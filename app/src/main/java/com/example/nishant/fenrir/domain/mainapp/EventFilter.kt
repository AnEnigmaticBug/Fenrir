package com.example.nishant.fenrir.domain.mainapp

import org.threeten.bp.LocalDateTime

/**
 * @property category          : If this is null, no filtering will occur based on the category.
 * @property venue             : If this is null, no filtering will occur based on the venue.
 * @property showOnlyOngoing   : If this is true, only the ongoing events will be allowed.
 * @property showOnlySubscribed: If this is true, only subscribed events will be allowed.
 * */
data class EventFilter(val category: Category?, val venue: Venue?, val showOnlyOngoing: Boolean, val showOnlySubscribed: Boolean) {

    fun isSatisfiedByEvent(event: Event): Boolean {
        var categoryResult = true
        if(category != null) {
            categoryResult = event.category == category
        }
        var venueResult = true
        if(venue != null) {
            venueResult = event.venue == venue
        }
        var ongoingResult = true
        if(showOnlyOngoing) {
            ongoingResult = when(event.duration) {
                null -> false
                else -> {
                    if(event.date == null || event.time == null) {
                        false
                    }
                    else {
                        val eDt = LocalDateTime.of(event.date, event.time)
                        val now = LocalDateTime.now()
                        eDt < now && now < eDt.plusMinutes(event.duration.toLong())
                    }
                }
            }
        }
        var subscribedResult = true
        if(showOnlySubscribed) {
            subscribedResult = event.subscribed
        }
        return categoryResult && venueResult && ongoingResult && subscribedResult
    }
}