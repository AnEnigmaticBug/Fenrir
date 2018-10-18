package com.example.nishant.fenrir.data.retrofit

import android.content.Context
import android.net.ConnectivityManager

class NetworkWatcherImpl(private val context: Context) : NetworkWatcher {

    override fun checkIfConnectedToInternet(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}