package com.example.nishant.fenrir.data

import com.example.nishant.fenrir.domain.UserPreferences
import io.reactivex.Completable
import io.reactivex.Flowable

interface CentralRepository {

    fun getUserPreferences(): Flowable<UserPreferences>

    fun setUserPreferences(userPreferences: UserPreferences): Completable
}