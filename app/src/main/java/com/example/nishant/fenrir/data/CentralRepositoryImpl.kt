package com.example.nishant.fenrir.data

import android.annotation.SuppressLint
import android.content.Context
import com.example.nishant.fenrir.domain.Category
import com.example.nishant.fenrir.domain.EventFilter
import com.example.nishant.fenrir.domain.UserPreferences
import com.example.nishant.fenrir.domain.Venue
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

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
}