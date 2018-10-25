package com.example.nishant.fenrir.screens.mainapp.more.developers

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_developers.view.*

class DevelopersFragment : Fragment() {

    private lateinit var viewModel: DevelopersViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this)[DevelopersViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_developers, container, false)

        rootPOV.developersRCY.adapter = DevelopersAdapter()

        viewModel.developers.observe(this, Observer {
            (rootPOV.developersRCY.adapter as DevelopersAdapter).developers = it?: listOf()
        })

        return rootPOV
    }
}