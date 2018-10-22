package com.example.nishant.fenrir.screens.mainapp.profile.signedeventlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavigationHost
import kotlinx.android.synthetic.main.fra_signed_event_list.view.*

class SignedEventListFragment : Fragment() {

    private lateinit var navigationHost: NavigationHost
    private lateinit var viewModel: SignedEventListViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navigationHost = parentFragment as NavigationHost

        viewModel = ViewModelProviders.of(this, SignedEventListViewModelFactory())[SignedEventListViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_signed_event_list, container, false)

        rootPOV.closeBTN.setOnClickListener {
            navigationHost.exit()
        }

        rootPOV.signedEventsRCY.adapter = SignedEventsAdapter()

        viewModel.events.observe(this, Observer {
            (rootPOV.signedEventsRCY.adapter as SignedEventsAdapter).events = it?: listOf()
        })

        return rootPOV
    }
}