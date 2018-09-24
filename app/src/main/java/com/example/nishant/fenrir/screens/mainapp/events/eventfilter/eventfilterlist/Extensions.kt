package com.example.nishant.fenrir.screens.mainapp.events.eventfilter.eventfilterlist

import com.example.nishant.fenrir.domain.mainapp.Category
import com.example.nishant.fenrir.domain.mainapp.Venue

fun Category.prettyString() = when(this) {
    Category.Dance -> "Dance"
    Category.Drama -> "Drama"
    Category.Films -> "Films"
    Category.Music -> "Music"
}

fun Venue.prettyString() = when(this) {
    Venue.Audi    -> "Audi"
    Venue.MLawns  -> "M Lawns"
    Venue.Rotunda -> "Rotunda"
    Venue.SACHall -> "SAC Hall"
    Venue.Stalls  -> "Stalls"
}