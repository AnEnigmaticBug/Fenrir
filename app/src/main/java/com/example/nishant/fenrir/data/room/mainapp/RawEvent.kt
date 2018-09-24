package com.example.nishant.fenrir.data.room.mainapp

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.nishant.fenrir.domain.mainapp.Category
import com.example.nishant.fenrir.domain.mainapp.Event
import com.example.nishant.fenrir.domain.mainapp.Venue
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@Entity(tableName = "events")
data class RawEvent(@PrimaryKey val id: String, val name: String, val about: String, val rules: String, val venue: Venue?, val category: Category, val date: LocalDate?, val time: LocalTime?, val duration: Int?, val subscribed: Boolean) {

    fun toEvent() = Event(id, name, about, rules, venue, category, date, time, duration, subscribed)
}