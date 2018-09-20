package com.example.nishant.fenrir.navigation

import android.os.Bundle
import android.support.v4.app.Fragment

fun Fragment.withBundle(bundle: Bundle?) = this.also { it.arguments = bundle }