package com.example.nishant.fenrir.data.room.mainapp

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface MainAppDao {

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flowable<List<RawEvent>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: String): Flowable<RawEvent>

    @Query("SELECT COUNT(id) FROM events WHERE id = :id")
    fun eventExists(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(event: RawEvent)

    @Update
    fun updateEvent(event: RawEvent)

    @Delete
    fun deleteEvent(event: RawEvent)

    @Query("SELECT * FROM signedEvents")
    fun getAllSignedEvents(): Flowable<List<RawSignedEvent>>

    @Query("SELECT * FROM signedEvents WHERE id = :id")
    fun getSignedEventById(id: String): Flowable<RawSignedEvent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSignedEvent(event: RawSignedEvent)

    @Update
    fun updateSignedEvent(event: RawSignedEvent)

    @Delete
    fun deleteSignedEvent(event: RawSignedEvent)
}