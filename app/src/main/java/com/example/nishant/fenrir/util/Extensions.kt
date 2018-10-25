package com.example.nishant.fenrir.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import java.lang.RuntimeException

fun<T> LiveData<T>.toMut(): MutableLiveData<T> = when(this) {
    is MutableLiveData -> this
    else               -> throw ClassCastException("Can't cast ${this::class.java.simpleName} to MutableLiveData")
}

/**
 * This is to automatically dispose any previous subscription if a new subscription is set using this method.
 * */
fun CompositeDisposable.set(disposable: Disposable) {
    this.clear()
    this.add(disposable)
}

private fun Int.doubleDigitString() = when(this) {
    in 0..9   -> "0$this"
    in 10..99 -> this.toString()
    else      -> throw IllegalArgumentException("Can't doubleDigitify $this")
}

fun LocalTime?.prettyString(): String {
    if(this != null) {
        val suffix = when(this.hour) {
            in 0..11 -> "AM"
            else     -> "PM"
        }
        val hour = when(this.hour) {
            0        -> "12"
            in 1..12 -> this.hour.doubleDigitString()
            else     -> (this.hour % 12).doubleDigitString()
        }
        return "$hour:${this.minute.doubleDigitString()} $suffix"
    }
    return "TBA"
}

fun LocalDate?.prettyString(): String {
    if(this != null) {
        val monthStr = when(this.monthValue) {
            1    -> "January"
            2    -> "February"
            3    -> "March"
            4    -> "April"
            5    -> "May"
            6    -> "June"
            7    -> "July"
            8    -> "August"
            9    -> "September"
            10    -> "October"
            11   -> "November"
            12   -> "December"
            else -> throw RuntimeException("Wtf just happened? Month value: ${this.monthValue}")
        }
        return "$monthStr ${this.dayOfMonth}"
    }
    return "TBA"
}