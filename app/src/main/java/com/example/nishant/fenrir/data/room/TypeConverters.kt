package com.example.nishant.fenrir.data.room

import android.arch.persistence.room.TypeConverter
import com.example.nishant.fenrir.domain.Category
import com.example.nishant.fenrir.domain.Venue
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class TypeConverters {

    @TypeConverter
    fun fromStringToVenue(s: String): Venue? = if(s == "NULL") { null } else { Venue.valueOf(s) }

    @TypeConverter
    fun fromVenueToString(v: Venue?): String = if(v == null) { "NULL" } else { v.toString() }

    @TypeConverter
    fun fromStringToCategory(s: String): Category = Category.valueOf(s)

    @TypeConverter
    fun fromCategoryToString(c: Category): String = c.toString()

    @TypeConverter
    fun fromStringToLocalDate(s: String): LocalDate? = if(s == "NULL") { null } else { LocalDate.parse(s) }

    @TypeConverter
    fun fromLocalDateToString(d: LocalDate?): String = if(d == null) { "NULL" } else { d.toString() }

    @TypeConverter
    fun fromStringToLocalTime(s: String): LocalTime? = if(s == "NULL") { null } else { LocalTime.parse(s) }

    @TypeConverter
    fun fromLocalTimeToString(t: LocalTime?): String = if(t == null) { "NULL" } else { t.toString() }
}