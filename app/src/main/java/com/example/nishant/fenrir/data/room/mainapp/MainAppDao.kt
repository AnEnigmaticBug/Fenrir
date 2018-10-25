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

    @Query("SELECT * FROM comedians")
    fun getAllComedians(): Flowable<List<RawComedian>>

    @Query("SELECT * FROM comedians WHERE name = :name")
    fun getComedianByName(name: String): Flowable<RawComedian>

    @Query("SELECT COUNT(name) FROM comedians WHERE name = :name")
    fun comedianExists(name: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComedian(comedian: RawComedian)

    @Update
    fun updateComedian(comedian: RawComedian)

    @Delete
    fun deleteComedian(comedian: RawComedian)

    @Query("SELECT * FROM payloadedNotifications ORDER BY datetime DESC")
    fun getAllPayloadedNotifications(): Flowable<List<RawPayloadedNotification>>

    @Query("SELECT * FROM payloadedNotifications WHERE id = :id")
    fun getPayloadedNotificationById(id: String): Flowable<RawPayloadedNotification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayloadedNotification(notification: RawPayloadedNotification)

    @Update
    fun updatePayloadedNotification(notification: RawPayloadedNotification)

    @Delete
    fun deletePayloadedNotification(notification: RawPayloadedNotification)
}