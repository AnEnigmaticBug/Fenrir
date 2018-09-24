package com.example.nishant.fenrir.domain.mainapp

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

/**
 * @property time is the starting time of the event.
 * @property duration is the duration of the event in minutes.
 * */
data class Event(val id: String, val name: String, val about: String, val rules: String, val venue: Venue?, val category: Category, val date: LocalDate?, val time: LocalTime?, val duration: Int?, val subscribed: Boolean)