package com.example.nishant.fenrir.screens.mainapp.more.sponsors

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_sponsors.view.*

class SponsorsFragment : Fragment() {

    private lateinit var viewModel: SponsorsViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this)[SponsorsViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_sponsors, container, false)

        rootPOV.sponsorsRCY.adapter = SponsorsAdapter()

        viewModel.sponsors.observe(this, Observer {
            (rootPOV.sponsorsRCY.adapter as SponsorsAdapter).sponsors = it?: listOf()
        })

        return rootPOV
    }
}