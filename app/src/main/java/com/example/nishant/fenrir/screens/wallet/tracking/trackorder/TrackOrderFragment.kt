package com.example.nishant.fenrir.screens.wallet.tracking.trackorder

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.fenrir.R

class TrackOrderFragment : Fragment() {

    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootPOV = inflater.inflate(R.layout.fra_track_order, container, false)
        return rootPOV
    }
}