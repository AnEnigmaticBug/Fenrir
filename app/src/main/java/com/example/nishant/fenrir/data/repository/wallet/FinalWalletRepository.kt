package com.example.nishant.fenrir.data.repository.wallet

import android.annotation.SuppressLint
import android.util.Log
import com.example.nishant.fenrir.data.firestore.wallet.FireAccountant
import com.example.nishant.fenrir.data.firestore.wallet.FireTracker
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.data.retrofit.NetworkWatcher
import com.example.nishant.fenrir.data.retrofit.wallet.WalletService
import com.example.nishant.fenrir.data.room.wallet.*
import com.example.nishant.fenrir.domain.UserDetails
import com.example.nishant.fenrir.domain.wallet.BuyAttemptResult
import com.example.nishant.fenrir.domain.wallet.CartEntry
import com.example.nishant.fenrir.domain.wallet.Item
import com.example.nishant.fenrir.domain.wallet.Stall
import com.example.nishant.fenrir.domain.wallet.*
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import org.threeten.bp.LocalDateTime
import java.util.concurrent.TimeUnit

class FinalWalletRepository(private val networkWatcher: NetworkWatcher, private val cRepo: CentralRepository, private val fireAccountant: FireAccountant, private val walletService: WalletService, private val walletDao: WalletDao, fireTracker: FireTracker) : WalletRepository {

    init {
        fireTracker.getTrackedEntries()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError { Log.e("CANDY", it.message) }
                .subscribe {
                    it.forEach { new ->
                        val id = new.orderId + new.itemId
                        if(walletDao.trackedEntryExists(id)) {
                            walletDao.getAllTrackedEntries()
                                    .take(1)
                                    .doOnError { Log.e("CANDY", it.message) }
                                    .subscribe {
                                        val old = it.first { it.id == id }
                                        walletDao.updateTrackedEntry(
                                                RawTrackedEntry(
                                                        id,
                                                        new.orderId,
                                                        new.itemId,
                                                        new.stallId,
                                                        new.quantity,
                                                        new.status,
                                                        new.otp,
                                                        old.otpShown,
                                                        new.orderDate,
                                                        new.orderTime
                                                )
                                        )
                                    }
                        }
                        else {
                            walletDao.insertTrackedEntry(
                                    RawTrackedEntry(new.orderId + new.itemId, new.orderId, new.itemId, new.stallId, new.quantity, new.status, new.otp, false, new.orderDate, new.orderTime)
                            )
                        }
                    }
                }
    }

    override fun getBalance(): Flowable<Int> {
        return fireAccountant.getBalance().doOnError { Log.e("CANDY", it.message) }
    }

    override fun addMoney(amount: Int): Single<AddMoneyAttemptResult> {
        if(!networkWatcher.checkIfConnectedToInternet()) {
            return Single.just(AddMoneyAttemptResult.Failure.NoInternet)
        }
        val requestBody = JsonObject().also {
            it.addProperty("amount", amount)
            it.addProperty("is_swd", true)
        }
        return cRepo.getUserDetails()
                .subscribeOn(Schedulers.io())
                .flatMapSingle { walletService.addMoney(it.jwtToken, requestBody) }
                .doOnError { Log.e("CANDY", it.message) }
                .map {
                    when(it.isSuccessful) {
                        true  -> AddMoneyAttemptResult.Success
                        false -> throw IllegalStateException("Strange error body: ${it.errorBody()!!.string()}")
                    }
                }
    }

    override fun transferMoney(receiverID: String, amount: Int): Single<TransferMoneyAttemptResult> {
        if(!networkWatcher.checkIfConnectedToInternet()) {
            return Single.just(TransferMoneyAttemptResult.Failure.NoInternet)
        }
        return cRepo.getUserDetails()
                .subscribeOn(Schedulers.io())
                .flatMapSingle { userDetails ->
                    val requestBody = JsonObject().also {
                        it.addProperty("target_user", receiverID)
                        it.addProperty("amount", amount)
                    }
                    walletService.transferMoney(userDetails.jwtToken, requestBody)
                }
                .doOnError { Log.e("CANDY", it.message) }
                .map {
                    when(it.isSuccessful) {
                        true  -> TransferMoneyAttemptResult.Success
                        false -> {
                            val errorBody = it.errorBody()!!.string()
                            when {
                                errorBody.contains("can't transfer money to yourself") -> TransferMoneyAttemptResult.Failure.SendToSelf
                                errorBody.contains("Invalid action. You can only tra") -> TransferMoneyAttemptResult.Failure.SwdProblem
                                else                                                         -> throw IllegalStateException("Strange error body: $errorBody")
                            }
                        }
                    }
                }
    }

    @SuppressLint("CheckResult")
    override fun getAllStalls(): Flowable<List<Stall>> {
        if(networkWatcher.checkIfConnectedToInternet()) {
            cRepo.getUserDetails()
                    .subscribeOn(Schedulers.io())
                    .doOnError { Log.e("CANDY", it.message) }
                    .subscribe(
                            {
                                walletService.getAllStalls(it.jwtToken)
                                        .subscribeOn(Schedulers.io())
                                        .doOnError { Log.e("CANDY", it.message) }
                                        .subscribe(
                                                { _stalls ->
                                                    if(_stalls.isSuccessful) {
                                                        _stalls.body()!!.forEach {
                                                            walletDao.insertStall(RawStall(it.id.toString(), it.name, it.description))
                                                        }
                                                    }
                                                },
                                                {
                                                    Log.e("CANDY", it.message)
                                                }
                                        )
                            },
                            {
                                throw IllegalStateException("Wallet accessed without logging in.")
                            }
                    )
        }

        return walletDao.getAllStalls().map { it.map { Stall(it.id, it.name, it.description) } }
                .subscribeOn(Schedulers.io())
                .debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("CANDY", it.message) }
    }

    override fun getStallById(stallId: String): Flowable<Stall> {
        return walletDao.getStallById(stallId).map { Stall(it.id, it.name, it.description) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("CANDY", it.message) }
    }

    @SuppressLint("CheckResult")
    override fun getAllItemsInStallOfId(stallId: String): Flowable<List<Item>> {
        if(networkWatcher.checkIfConnectedToInternet()) {
            cRepo.getUserDetails()
                    .subscribeOn(Schedulers.io())
                    .doOnError { Log.e("CANDY", it.message) }
                    .subscribe(
                            {
                                walletService.getAllItemsInStallOfId(it.jwtToken, stallId)
                                        .subscribeOn(Schedulers.io())
                                        .doOnError { Log.e("CANDY", it.message) }
                                        .subscribe(
                                                { _items ->
                                                    if(_items.isSuccessful) {
                                                        _items.body()!!.forEach {
                                                            walletDao.insertItem(RawItem(it.id, it.name, it.price, it.isAvailable, stallId))
                                                        }
                                                    }
                                                },
                                                {
                                                    Log.e("CANDY", it.message)
                                                }
                                        )
                            },
                            {
                                throw IllegalStateException("Wallet accessed without logging in.")
                            }
                    )
        }

        return walletDao.getAllItemsInStallOfId(stallId).map { it.map { Item(it.id, it.name, it.price, it.isAvailable, it.stallId) } }
                .subscribeOn(Schedulers.io())
                .debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("CANDY", it.message) }
    }

    override fun getItemByIdInStallOfId(stallId: String, itemId: String): Flowable<Item> {
        return walletDao.getItemByIdInStallOfId(stallId, itemId).map { Item(it.id, it.name, it.price, it.isAvailable, it.stallId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("CANDY", it.message) }
    }

    override fun getAllEntriesInCart(): Flowable<List<CartEntry>> {
        return walletDao.getAllCartEntries().map { it.map { CartEntry(it.id, Item(it.itemId, it.itemName, it.itemPrice, true, it.stallId), it.quantity, it.isValid) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("CANDY", it.message) }
    }

    override fun addEntryToCart(entry: CartEntry): Completable {
        return Completable.fromAction {
            walletDao.insertCartEntry(RawCartEntry(entry.id, entry.item.id, entry.item.name, entry.item.price, entry.item.stallId, entry.quantity, entry.isValid))
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("CANDY", it.message) }
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
                .doOnError { Log.e("CANDY", it.message) }
    }

    @SuppressLint("CheckResult")
    override fun orderAllEntriesInCart(): Single<BuyAttemptResult> {
        if(!networkWatcher.checkIfConnectedToInternet()) {
            return Single.just(BuyAttemptResult.Failure.NoInternet)
        }

        return cRepo.getUserDetails()
                .subscribeOn(Schedulers.io())
                .toSingle()
                .zipWith(walletDao.getAllCartEntries().firstOrError(), BiFunction { t1: UserDetails, t2: List<RawCartEntry> -> Pair(t1, t2) })
                .flatMap {
                    if(it.second.isEmpty()) {
                        Single.just(BuyAttemptResult.Failure.VacantCart)
                    }
                    else {
                        walletService.placeOrder(it.first.jwtToken, JsonParser().parse(makeJson(it.second).toString()) as JsonObject)
                                .map {
                                    when(it.isSuccessful) {
                                        true  -> BuyAttemptResult.Success
                                        false -> {
                                            val errorBody = it.errorBody()!!.string()
                                            when {
                                                errorBody.contains("Insufficient balance") -> {
                                                    BuyAttemptResult.Failure.LessBudget
                                                }
                                                errorBody.contains("invalidItemIds")       -> {

                                                    val ids = JsonParser().parse(errorBody).asJsonObject
                                                            .getAsJsonArray("unavailable").map {
                                                                it.asString
                                                            }
                                                    BuyAttemptResult.Failure.OutOfStock(ids)
                                                }
                                                else                                             -> {
                                                    throw IllegalStateException("Strange errorBody: ${errorBody}")
                                                }
                                            }
                                        }
                                    }
                                }
                    }
                }
                .doOnError { Log.d("CANDY", it.message) }
    }

    private fun makeJson(cartEntries: List<RawCartEntry>): JSONObject {
        val datetime = with(LocalDateTime.now()) {
            "${this.toLocalDate()} at $hour:$minute:$second"
        }
        return JSONObject().also { _body ->
            _body.put("date", datetime)
            _body.put("price", "0")
            _body.put("order", JSONObject().also { _order ->
                cartEntries.distinctBy { it.stallId }.map { it.stallId }.forEach { _stallId ->
                    _order.put(_stallId, JSONObject().also { _stallData ->
                        _stallData.put("items", JSONArray().also { _items ->
                            cartEntries.filter { it.stallId == _stallId }.forEach { _cartEntry ->
                                _items.put(JSONObject().also { _item ->
                                    _item.put("id", _cartEntry.itemId.toInt())
                                    _item.put("qty", _cartEntry.quantity)
                                })
                            }
                        })
                    })
                }
            })
        }
    }

    override fun invalidateEntryWithItemId(itemId: String): Completable {
        return Completable.fromAction {
            walletDao.getAllCartEntries()
                    .take(1)
                    .flatMap { Flowable.fromIterable(it) }
                    .filter { it.itemId == itemId }
                    .doOnError { Log.d("CANDY", it.message) }
                    .blockingSubscribe {
                        walletDao.updateCartEntry(it.copy(isValid = false))
                    }
        }
    }

    override fun getAllTrackedEntries(): Flowable<List<TrackedEntry>> {
        return Flowable.combineLatest(walletDao.getAllTrackedEntries(), walletDao.getAllItems(), BiFunction { t1: List<RawTrackedEntry>, t2: List<RawItem> -> Pair(t1, t2) })
                .distinctUntilChanged()
                .debounce(200, TimeUnit.MILLISECONDS)
                .flatMap { pair ->
                    Log.d("CANDY", "fst: ${pair.first.size} snd: ${pair.second.size}")
                    if(pair.first.any { it.itemId !in pair.second.map { it.id } }) {
                        if(pair.first.any { it.stallId !in pair.second.map { it.stallId } }) {
                            cRepo.getUserDetails()
                                    .toSingle()
                                    .flatMap { walletService.getAllStalls(it.jwtToken) }
                                    .subscribe(
                                            { stalls ->
                                                if(stalls.isSuccessful) {
                                                    stalls.body()!!.forEach {
                                                        walletDao.insertStall(RawStall(it.id.toString(), it.name, it.description))
                                                    }
                                                }
                                            },
                                            {
                                                Log.e("CANDY", "Thread interrupted")
                                            }
                                    )
                        }

                        pair.first.filter { it.itemId !in pair.second.map { it.id } }.forEach { rte ->
                            cRepo.getUserDetails()
                                    .toSingle()
                                    .flatMap { walletService.getAllItemsInStallOfId(it.jwtToken, rte.stallId) }
                                    .subscribe(
                                            { items ->
                                                if (items.isSuccessful) {
                                                    items.body()!!.forEach {
                                                        walletDao.insertItem(RawItem(it.id, it.name, it.price, it.isAvailable, rte.stallId))
                                                    }
                                                }
                                            },
                                            {
                                                Log.e("CANDY", "Thread interrupted")
                                            }
                                    )

                        }
                        Flowable.just(pair.first.filter { it.itemId in pair.second.map { it.id } }.map { rte ->
                            TrackedEntry(rte.id, rte.orderId, pair.second.first { it.id == rte.itemId }.toItem(), rte.quantity, rte.status, rte.otp, rte.otpShown, rte.orderDate, rte.orderTime)
                        })
                        }
                    else {
                        Flowable.just(pair.first.map { rte ->
                            TrackedEntry(rte.id, rte.orderId, pair.second.first { it.id == rte.itemId }.toItem(), rte.quantity, rte.status, rte.otp, rte.otpShown, rte.orderDate, rte.orderTime) })
                    }

                }
                .doOnError { Log.e("CANDY", it.message) }
    }

    override fun getTrackedEntryById(id: String): Flowable<TrackedEntry> {
        return getAllTrackedEntries()
                .flatMap { Flowable.fromIterable(it) }
                .filter { it.id == id }
                .doOnError { Log.e("CANDY", it.message) }
    }

    private fun RawItem.toItem(): Item {
        return Item(id, name, price, isAvailable, stallId)
    }

    override fun notifyOTPShown(entryId: String): Single<NotifyOTPResult> {
        if(!networkWatcher.checkIfConnectedToInternet()) {
            return Single.just(NotifyOTPResult.Failure.NoInternet)
        }
        return cRepo.getUserDetails()
                .subscribeOn(Schedulers.io())
                .toSingle()
                .zipWith(getTrackedEntryById(entryId).firstOrError(), BiFunction { t1: UserDetails, t2: TrackedEntry -> Pair(t1, t2) })
                .flatMap { pair ->
                    val requestBody = JsonObject().also {
                        it.addProperty("stall_id", pair.second.item.stallId.toInt())
                        it.addProperty("order_id", pair.second.orderId.toInt())
                    }
                    walletService.notifyOTPUsage(pair.first.jwtToken, requestBody)
                }
                .flatMap {
                    when(it.isSuccessful) {
                        true  -> {
                            walletDao.getTrackedEntryById(entryId)
                                    .subscribe {
                                        walletDao.updateTrackedEntry(it.copy(otpShown = true))
                                    }
                            Single.just(NotifyOTPResult.Success)
                        }
                        false -> Single.just(NotifyOTPResult.Failure.ServerBusy)
                    }
                }
                .doOnError { Log.d("CANDY", it.message) }
    }
}