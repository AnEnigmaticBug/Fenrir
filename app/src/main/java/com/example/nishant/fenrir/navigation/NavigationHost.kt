package com.example.nishant.fenrir.navigation

import android.os.Bundle

interface NavigationHost {

    fun show(destination: Destination, bundle: Bundle? = null)

    fun back(): Boolean

    fun exit()
}