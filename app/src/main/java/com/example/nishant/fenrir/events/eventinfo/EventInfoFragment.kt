package com.example.nishant.fenrir.events.eventinfo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_event_info.view.*

class EventInfoFragment : Fragment() {

    private lateinit var viewModel: EventInfoViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val id = arguments!!.getString("eventId")
        viewModel = ViewModelProviders.of(this, EventInfoViewModelFactory(id))[EventInfoViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_event_info, container, false)

        rootPOV.remindBTN.setOnClickListener {
            viewModel.toggleSubscription()
        }

        rootPOV.toggleBTN.setOnClickListener {
            viewModel.toggleMainContents()
        }

        viewModel.rawEvent.observe(this, Observer {
            rootPOV.nameLBL.text = it?.name
            rootPOV.venueLBL.text = it?.venue
            rootPOV.timeAndDayLBL.text = it?.timeAndDay
            rootPOV.mainContentLBL.text = it?.mainContent
        })

        viewModel.remindButtonText.observe(this, Observer {
            rootPOV.remindBTN.text = it
        })

        viewModel.toggleButtonText.observe(this, Observer {
            rootPOV.toggleBTN.text = it
        })

        // TODO: Add swipe up the close functionality.

        return rootPOV
    }
}