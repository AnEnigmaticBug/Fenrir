package com.example.nishant.fenrir.data.repository.wallet

import com.example.nishant.fenrir.domain.wallet.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class StubWalletRepository : WalletRepository {

    private val stalls = listOf(
            Stall("01", "Goosebumps", "Lite"),
            Stall("02", "Pizza hut", "Lite"),
            Stall("03", "Dominoes", "Lite"),
            Stall("04", "Mc Donalds", "Lite"),
            Stall("05", "The Souled Store", "Lite"),
            Stall("06", "Baskin Robbins", "Lite"),
            Stall("07", "Subway", "Lite")
    )

    private val items = listOf(
            Item("11", "Brownies", 50, stalls[0].id),
            Item("12", "Mc Spicy Paneer Burger", 40, stalls[3].id),
            Item("13", "Aloo Tikki Burger", 35, stalls[3].id),
            Item("14", "Mc Veggie", 50, stalls[3].id),
            Item("15", "Mc Puff", 45, stalls[3].id),
            Item("16", "Happy Meal", 100, stalls[3].id),
            Item("17", "Coca Cola", 30, stalls[1].id),
            Item("18", "Mineral Water", 120, stalls[4].id)
    )

    override fun getBalance(): Flowable<Int> {
        return Flowable.just(2500)
    }

    override fun addMoney(amount: Int): Single<AddMoneyAttemptResult> {
        return Single.just(AddMoneyAttemptResult.Success)
    }

    override fun transferMoney(receiverID: String, amount: Int): Single<TransferMoneyAttemptResult> {
        return Single.just(TransferMoneyAttemptResult.Success)
    }

    override fun getAllStalls(): Flowable<List<Stall>> {
        return Flowable.just(stalls)
    }

    override fun getStallById(stallId: String): Flowable<Stall> {
        return Flowable.just(stalls.first { it.id == stallId })
    }

    override fun getAllItemsInStallOfId(stallId: String): Flowable<List<Item>> {
        return Flowable.just(items.filter { it.stallId == stallId })
    }

    override fun getItemByIdInStallOfId(stallId: String, itemId: String): Flowable<Item> {
        return Flowable.just(items.first { it.stallId == stallId && it.id == itemId })
    }

    override fun getAllEntriesInCart(): Flowable<List<CartEntry>> {
        return Flowable.just(listOf(
                CartEntry("21", items[0], 3, true),
                CartEntry("22", items[3], 2, true),
                CartEntry("23", items[7], 1, false),
                CartEntry("24", items[1], 3, true)
        ))
    }

    override fun addEntryToCart(entry: CartEntry): Completable {
        return Completable.complete()
    }

    override fun removeEntryFromCartById(entryId: String): Completable {
        return Completable.complete()
    }

    override fun orderAllEntriesInCart(): Single<BuyAttemptResult> {
        return Single.timer(4, TimeUnit.SECONDS)
                .map { BuyAttemptResult.Success as BuyAttemptResult }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun invalidateEntryWithItemId(itemId: String): Completable {
        return Completable.complete()
    }
}