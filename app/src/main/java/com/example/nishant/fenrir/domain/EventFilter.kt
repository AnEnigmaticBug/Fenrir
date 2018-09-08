package com.example.nishant.fenrir.domain

import org.threeten.bp.LocalDateTime

/**
 * @property category      : If this is null, no filtering will occur based on the category.
 * @property venue         : If this is null, no filtering will occur based on the venue.
 * @property showOngoing   : If this is true, only the ongoing events will be allowed.
 * @property showSubscribed: If this is true, only subscribed events will be allowed.
 * */
data class EventFilter(private val category: Category?, private val venue: Venue?, private val showOngoing: Boolean, private val showSubscribed: Boolean) {

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
        if(showOngoing) {
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
        if(showSubscribed) {
            subscribedResult = event.subscribed
        }
        return categoryResult && venueResult && ongoingResult && subscribedResult
    }
}