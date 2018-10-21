package com.example.nishant.fenrir.data.repository.wallet

import com.example.nishant.fenrir.domain.wallet.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface WalletRepository {

    fun getBalance(): Flowable<Int>

    fun addMoney(amount: Int): Single<AddMoneyAttemptResult>

    fun transferMoney(receiverID: String, amount: Int): Single<TransferMoneyAttemptResult>

    fun getAllStalls(): Flowable<List<Stall>>

    fun getStallById(stallId: String): Flowable<Stall>

    fun getAllItemsInStallOfId(stallId: String): Flowable<List<Item>>

    fun getItemByIdInStallOfId(stallId: String, itemId: String): Flowable<Item>

    fun getAllEntriesInCart(): Flowable<List<CartEntry>>

    fun addEntryToCart(entry: CartEntry): Completable

    fun removeEntryFromCartById(entryId: String): Completable

    fun orderAllEntriesInCart(): Single<BuyAttemptResult>

    fun invalidateEntryWithItemId(itemId: String): Completable

    fun getAllTrackedEntries(): Flowable<List<TrackedEntry>>

    fun getTrackedEntryById(id: String): Flowable<TrackedEntry>

    fun notifyOTPShown(entryId: String): Single<NotifyOTPResult>
}