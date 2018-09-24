package com.example.nishant.fenrir.screens.mainapp.events.eventfilter.eventfiltermenu

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.screens.mainapp.events.eventfilter.eventfilterlist.FilterType
import com.example.nishant.fenrir.navigation.NavigationGraph
import com.example.nishant.fenrir.navigation.NavigationHost
import kotlinx.android.synthetic.main.fra_event_filter_menu.view.*

class EventFilterMenuFragment : Fragment() {

    private lateinit var navigationHost: NavigationHost
    private lateinit var viewModel: EventFilterMenuViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navigationHost = when(parentFragment) {
            is NavigationHost -> parentFragment as NavigationHost
            else              -> throw ClassCastException("Not a NavigationHost")
        }

        viewModel = ViewModelProviders.of(this, EventFilterMenuViewModelFactory())[EventFilterMenuViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_event_filter_menu, container, false)

        rootPOV.closeBTN.setOnClickListener {
            navigationHost.exit()
        }

        rootPOV.showVenueFiltersBTN.setOnClickListener {
            val bundle = Bundle().also { it.putString("TYPE", FilterType.Venue.toString()) }
            navigationHost.show(NavigationGraph.Events.EventFilter.FILTER_LIST, bundle)
        }

        rootPOV.showCategoryFiltersBTN.setOnClickListener {
            val bundle = Bundle().also { it.putString("TYPE", FilterType.Category.toString()) }
            navigationHost.show(NavigationGraph.Events.EventFilter.FILTER_LIST, bundle)
        }

        rootPOV.showOnlyOngoingBTN.setOnClickListener {
            viewModel.toggleShowOnlyOngoingFilter()
        }

        rootPOV.showOnlyFavoritesBTN.setOnClickListener {
            viewModel.toggleShowOnlySubscribedFilter()
        }

        viewModel.showOnlyOngoing.observe(this, Observer {
            rootPOV.showOnlyOngoingBTN.setActiveStyle(it?:false)
        })

        viewModel.showOnlySubscribed.observe(this, Observer {
            rootPOV.showOnlyFavoritesBTN.setActiveStyle(it?:false)
        })

        return rootPOV
    }

    private fun TextView.setActiveStyle(value: Boolean) {
        when(value) {
            true  -> {
                this.setTextColor(resources.getColor(R.color.red01))
                this.setBackgroundResource(R.drawable.sh_capsule_wht01)
            }
            false -> {
                this.setTextColor(resources.getColor(R.color.wht01))
                this.setBackgroundResource(R.drawable.sh_capsule_border)
            }
        }
    }
}