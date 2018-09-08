package com.example.nishant.fenrir.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun<T> LiveData<T>.toMut(): MutableLiveData<T> = when(this) {
    is MutableLiveData -> this
    else               -> throw ClassCastException("Can't cast ${this::class.java.simpleName} to MutableLiveData")
}

/**
 * This is to automatically dispose any previous subscription if a new subscription is set using this method.
 * */
fun CompositeDisposable.set(disposable: Disposable) {
    this.clear()
    this.add(disposable)
}