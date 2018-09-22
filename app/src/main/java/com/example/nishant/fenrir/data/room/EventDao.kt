package com.example.nishant.fenrir.data.room

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flowable<List<RawEvent>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: String): Flowable<RawEvent>

    @Query("SELECT COUNT(id) FROM events WHERE id = :id")
    fun eventExists(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: RawEvent)

    @Update
    fun update(event: RawEvent)

    @Delete
    fun delete(event: RawEvent)
}