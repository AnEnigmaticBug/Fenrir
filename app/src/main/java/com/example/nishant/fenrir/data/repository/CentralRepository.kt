package com.example.nishant.fenrir.data.repository

import com.example.nishant.fenrir.domain.UserDetails
import com.example.nishant.fenrir.domain.UserPreferences
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface CentralRepository {

    fun getUserPreferences(): Flowable<UserPreferences>

    fun setUserPreferences(userPreferences: UserPreferences): Completable

    fun getWalletToken(): Single<String>

    fun getUserDetails(): Maybe<UserDetails>

    fun setUserDetails(userDetails: UserDetails): Completable
}