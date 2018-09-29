package com.example.nishant.fenrir.data.repository.wallet

import android.content.Context
import com.example.nishant.fenrir.data.room.wallet.RawItem
import com.example.nishant.fenrir.data.room.wallet.RawCartEntry
import com.example.nishant.fenrir.data.room.wallet.RawStall
import com.example.nishant.fenrir.data.room.wallet.WalletDao
import com.example.nishant.fenrir.domain.wallet.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RoomWalletRepository(private val context: Context, private val walletDao: WalletDao) : WalletRepository {

    private val stalls = listOf(
            RawStall("01", "Goosebumps", "Lite"),
            RawStall("02", "Pizza hut", "Lite"),
            RawStall("03", "Dominoes", "Lite"),
            RawStall("04", "Mc Donalds", "Lite"),
            RawStall("05", "The Souled Store", "Lite"),
            RawStall("06", "Baskin Robbins", "Lite"),
            RawStall("07", "Subway", "Lite")
    )

    private val items = listOf(
            RawItem("11", "Brownies", 50, stalls[0].id),
            RawItem("12", "Mc Spicy Paneer Burger", 40, stalls[3].id),
            RawItem("13", "Aloo Tikki Burger", 35, stalls[3].id),
            RawItem("14", "Mc Veggie", 50, stalls[3].id),
            RawItem("15", "Mc Puff", 45, stalls[3].id),
            RawItem("16", "Happy Meal", 100, stalls[3].id),
            RawItem("17", "Coca Cola", 30, stalls[1].id),
            RawItem("18", "Mineral Water", 120, stalls[4].id)
    )

    init {
        val sp = context.getSharedPreferences("first_use", Context.MODE_PRIVATE)
        if(!sp.contains("firstUse")) {
            sp.edit().putBoolean("firstUse", false).apply()
            Flowable.just(1)
                    .observeOn(Schedulers.io())
                    .subscribe {
                        stalls.forEach {
                            walletDao.insertStall(it)
                        }
                        items.forEach {
                            walletDao.insertItem(it)
                        }
                    }
        }
    }

    override fun getBalance(): Flowable<Int> {
        return Flowable.just(500)
    }

    override fun addMoney(amount: Int): Single<AddMoneyAttemptResult> {
        return Single.just(AddMoneyAttemptResult.Success)
    }

    override fun transferMoney(receiverID: String, amount: Int): Single<TransferMoneyAttemptResult> {
        return Single.just(TransferMoneyAttemptResult.Success)
    }

    override fun getAllStalls(): Flowable<List<Stall>> {
        return walletDao.getAllStalls().map { it.map { Stall(it.id, it.name, it.description) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getStallById(stallId: String): Flowable<Stall> {
        return walletDao.getStallById(stallId).map { Stall(it.id, it.name, it.description) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllItemsInStallOfId(stallId: String): Flowable<List<Item>> {
        return walletDao.getAllItemsInStallOfId(stallId).map { it.map { Item(it.id, it.name, it.price, it.stallId) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getItemByIdInStallOfId(stallId: String, itemId: String): Flowable<Item> {
        return walletDao.getItemByIdInStallOfId(stallId, itemId).map { Item(it.id, it.name, it.price, it.stallId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllEntriesInCart(): Flowable<List<CartEntry>> {
        return walletDao.getAllCartEntries().map { it.map { CartEntry(it.id, Item(it.itemId, it.itemName, it.itemPrice, it.stallId), it.quantity, it.isValid) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addEntryToCart(entry: CartEntry): Completable {
        return Completable.fromAction {
            walletDao.insertCartEntry(RawCartEntry(entry.id, entry.item.id, entry.item.name, entry.item.price, entry.item.stallId, entry.quantity, entry.isValid))
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeEntryFromCartById(entryId: String): Completable {
        return Completable.fromAction {
            walletDao.getCartEntryById(entryId)
                    .take(1)
                    .blockingSubscribe {
                        walletDao.deleteCartEntry(it)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun orderAllEntriesInCart(): Single<BuyAttemptResult> {
        return Single.timer(4, TimeUnit.SECONDS)
                .map { BuyAttemptResult.Success as BuyAttemptResult }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun invalidateEntryWithItemId(itemId: String): Completable {
        return Completable.fromAction {
            walletDao.getAllCartEntries()
                    .take(1)
                    .flatMap { Flowable.fromIterable(it) }
                    .filter { it.itemId == itemId }
                    .blockingSubscribe {
                        walletDao.updateCartEntry(it.copy(isValid = false))
                    }
        }
    }
}