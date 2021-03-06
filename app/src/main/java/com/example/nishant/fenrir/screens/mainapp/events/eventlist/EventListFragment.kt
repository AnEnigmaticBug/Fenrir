package com.example.nishant.fenrir.screens.mainapp.events.eventlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.*
import com.example.nishant.fenrir.util.Constants
import kotlinx.android.synthetic.main.fra_event_list.view.*

class EventListFragment : NavHostFragment(), EventsAdapter.ClickListener {

    private lateinit var viewModel: EventListViewModel
    private lateinit var rootPOV: View

    override val navViewId = R.id.filterHostFRM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, EventListViewModelFactory())[EventListViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_event_list, container, false)

        rootPOV.eventsRCY.adapter = EventsAdapter(this)

        rootPOV.filterBTN.setOnClickListener {
            rootPOV.screenFaderPOV.visibility = View.VISIBLE
            show(NavigationGraph.MainApp.Events.EventFilter.FILTER_MENU)
        }

        rootPOV.screenFaderPOV.setOnClickListener {
            exit()
        }

        rootPOV.date0BTN.setOnClickListener {
            viewModel.changeDateToIndex(0)
        }

        rootPOV.date1BTN.setOnClickListener {
            viewModel.changeDateToIndex(1)
        }

        rootPOV.date2BTN.setOnClickListener {
            viewModel.changeDateToIndex(2)
        }

        rootPOV.date3BTN.setOnClickListener {
            viewModel.changeDateToIndex(3)
        }

        rootPOV.date4BTN.setOnClickListener {
            viewModel.changeDateToIndex(4)
        }

        viewModel.dateTitle.observe(this, Observer {
            rootPOV.dateTitleLBL.text = it
            when(it!!.takeLast(2).toInt()) {
                Constants.festDates[0].dayOfMonth -> moveUnderlineToDate(0)
                Constants.festDates[1].dayOfMonth -> moveUnderlineToDate(1)
                Constants.festDates[2].dayOfMonth -> moveUnderlineToDate(2)
                Constants.festDates[3].dayOfMonth -> moveUnderlineToDate(3)
                Constants.festDates[4].dayOfMonth -> moveUnderlineToDate(4)
            }
        })

        viewModel.rawEvents.observe(this, Observer {
            (rootPOV.eventsRCY.adapter as EventsAdapter).events = it?: listOf()
        })

        return rootPOV
    }

    private fun moveUnderlineToDate(i: Int) {
        val ctl = rootPOV as ConstraintLayout
        val constraintSet = ConstraintSet().also {
            it.clone(ctl)
            val btnId = when (i) {
                0 -> R.id.date0BTN
                1 -> R.id.date1BTN
                2 -> R.id.date2BTN
                3 -> R.id.date3BTN
                4 -> R.id.date4BTN
                else -> throw IllegalArgumentException("Can't move underline to $i")
            }
            it.connect(R.id.dateUnderLinePOV, ConstraintSet.TOP, btnId, ConstraintSet.BOTTOM, 4)
        }

        TransitionManager.beginDelayedTransition(ctl)
        constraintSet.applyTo(ctl)
        val resId = when(i) {
            0    -> R.color.vio01
            1    -> R.color.grn01
            2    -> R.color.yel01
            3    -> R.color.blu01
            4    -> R.color.red01
            else -> throw IllegalArgumentException("Can't move underline to $i")
        }
        rootPOV.dateBarPOV.setBackgroundColor(resources.getColor(resId))
    }

    override fun showDetailsForEventWithId(id: String) {
        val bundle = Bundle().also { it.putString("eventId", id) }
        rootPOV.findNavController().navigate(R.id.action_eventListFragment_to_eventInfoFragment, bundle)
    }

    override fun exit() {
        super.exit()
        rootPOV.screenFaderPOV.visibility = View.GONE
    }
}