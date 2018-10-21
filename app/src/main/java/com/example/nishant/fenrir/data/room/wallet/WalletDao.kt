package com.example.nishant.fenrir.data.room.wallet

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface WalletDao {

    @Query("SELECT * FROM stalls")
    fun getAllStalls(): Flowable<List<RawStall>>

    @Query("SELECT * FROM stalls WHERE id = :stallId")
    fun getStallById(stallId: String): Flowable<RawStall>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStall(stall: RawStall)

    @Update
    fun updateStall(stall: RawStall)

    @Delete
    fun deleteStall(stall: RawStall)

    @Query("SELECT * FROM items")
    fun getAllItems(): Flowable<List<RawItem>>

    @Query("SELECT * FROM items WHERE stallId = :stallId")
    fun getAllItemsInStallOfId(stallId: String): Flowable<List<RawItem>>

    @Query("SELECT * FROM items WHERE id = :itemId AND stallId = :stallId")
    fun getItemByIdInStallOfId(stallId: String, itemId: String): Flowable<RawItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: RawItem)

    @Update
    fun updateItem(item: RawItem)

    @Delete
    fun deleteItem(item: RawItem)

    @Query("SELECT * FROM rawCartEntries")
    fun getAllCartEntries(): Flowable<List<RawCartEntry>>

    @Query("SELECT * FROM rawCartEntries WHERE id = :entryId")
    fun getCartEntryById(entryId: String): Flowable<RawCartEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartEntry(cartEntry: RawCartEntry)

    @Update
    fun updateCartEntry(cartEntry: RawCartEntry)

    @Delete
    fun deleteCartEntry(cartEntry: RawCartEntry)

    @Query("SELECT * FROM trackedEntries")
    fun getAllTrackedEntries(): Flowable<List<RawTrackedEntry>>

    @Query("SELECT * FROM trackedEntries WHERE id = :entryId")
    fun getTrackedEntryById(entryId: String): Flowable<RawTrackedEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrackedEntry(cartEntry: RawTrackedEntry)

    @Update
    fun updateTrackedEntry(cartEntry: RawTrackedEntry)

    @Delete
    fun deleteTrackedEntry(cartEntry: RawTrackedEntry)

    @Query("SELECT COUNT(id) FROM trackedEntries WHERE id = :id")
    fun trackedEntryExists(id: String): Boolean
}