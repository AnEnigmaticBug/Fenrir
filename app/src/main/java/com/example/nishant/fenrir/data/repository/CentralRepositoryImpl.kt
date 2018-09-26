package com.example.nishant.fenrir.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.nishant.fenrir.domain.*
import com.example.nishant.fenrir.domain.mainapp.Category
import com.example.nishant.fenrir.domain.mainapp.EventFilter
import com.example.nishant.fenrir.domain.mainapp.Venue
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.security.Key

class CentralRepositoryImpl(private val context: Context) : CentralRepository {

    private val sp = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val userPrefsSubject = BehaviorSubject.create<UserPreferences>()

    private object Keys {

        object EventFilter {

            const val CATEGORY = "category"
            const val VENUE = "venue"
            const val SHOW_ONLY_ONGOING = "ongoing"
            const val SHOW_ONLY_SUBSCRIBED = "subscribed"
        }

        object UserDetails {

            const val ID = "id"
            const val JWT_TOKEN = "jwt"
            const val NAME = "name"
            const val IS_BITSIAN = "isBITSian"
            const val PROFILE_PIC_URL = "profilePicURL"
            const val QR_CODE_CONTENT = "qrCodeContent"
        }
    }

    override fun getUserPreferences(): Flowable<UserPreferences> {
        val c = try {
            Category.valueOf(sp.getString(Keys.EventFilter.CATEGORY, ""))
        }
        catch(e: IllegalArgumentException) {
            null
        }

        val v = try {
            Venue.valueOf(sp.getString(Keys.EventFilter.VENUE, ""))
        }
        catch(e: IllegalArgumentException) {
            null
        }

        val o = sp.getBoolean(Keys.EventFilter.SHOW_ONLY_ONGOING, false)
        val s = sp.getBoolean(Keys.EventFilter.SHOW_ONLY_SUBSCRIBED, false)
        userPrefsSubject.onNext(UserPreferences(EventFilter(c, v, o, s)))
        return userPrefsSubject.toFlowable(BackpressureStrategy.LATEST)
    }

    @SuppressLint("ApplySharedPref")
    override fun setUserPreferences(userPreferences: UserPreferences): Completable {
        return Completable.fromAction {
            userPrefsSubject.onNext(userPreferences)
            sp.edit().apply {
                putString(Keys.EventFilter.CATEGORY, userPreferences.eventFilter.category.toString())
                putString(Keys.EventFilter.VENUE, userPreferences.eventFilter.venue.toString())
                putBoolean(Keys.EventFilter.SHOW_ONLY_ONGOING, userPreferences.eventFilter.showOnlyOngoing)
                putBoolean(Keys.EventFilter.SHOW_ONLY_SUBSCRIBED, userPreferences.eventFilter.showOnlySubscribed)
            }.commit()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getWalletToken(): Single<String> {
        return Single.just("asdf")
    }

    override fun getUserDetails(): Maybe<UserDetails> {
        val id = sp.getString(Keys.UserDetails.ID, "")
        val jwtToken = sp.getString(Keys.UserDetails.JWT_TOKEN, "")
        if(id == "" || jwtToken == "") {
            return Maybe.empty()
        }
        val name = sp.getString(Keys.UserDetails.NAME, "")
        val isBITSian = sp.getBoolean(Keys.UserDetails.IS_BITSIAN, false)
        val profilePicURL = sp.getString(Keys.UserDetails.PROFILE_PIC_URL, "")
        val qrCodeContent = sp.getString(Keys.UserDetails.QR_CODE_CONTENT, "")
        return Maybe.just(UserDetails(id, jwtToken, name, isBITSian, profilePicURL, qrCodeContent))
    }

    @SuppressLint("ApplySharedPref")
    override fun setUserDetails(userDetails: UserDetails): Completable {
        return Completable.fromAction {
            sp.edit().apply {
                putString(Keys.UserDetails.ID, userDetails.id)
                putString(Keys.UserDetails.JWT_TOKEN, userDetails.jwtToken)
                putString(Keys.UserDetails.NAME, userDetails.name)
                putBoolean(Keys.UserDetails.IS_BITSIAN, userDetails.isBITSian)
                putString(Keys.UserDetails.PROFILE_PIC_URL, userDetails.profilePicURL)
                putString(Keys.UserDetails.QR_CODE_CONTENT, userDetails.qrCodeContent)
            }.commit()
        }
    }
}