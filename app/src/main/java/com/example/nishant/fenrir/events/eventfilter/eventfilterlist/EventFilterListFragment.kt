package com.example.nishant.fenrir.events.eventfilter.eventfilterlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_event_filter_list.view.*

class EventFilterListFragment : Fragment(), FilterItemsAdapter.ClickListener {

    interface Listener {

        fun onFilterMenuCloseBTNClicked()

        fun showFilterMenu()
    }

    private lateinit var listener: Listener
    private lateinit var viewModel: EventFilterListViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        listener = parentFragment as Listener

        val type = FilterType.valueOf(arguments!!.getString("TYPE", ""))
        viewModel = ViewModelProviders.of(this, EventFilterListViewModelFactory(type))[EventFilterListViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_event_filter_list, container, false)

        rootPOV.filterItemsRCY.adapter = FilterItemsAdapter(this)

        rootPOV.backBTN.setOnClickListener {
            listener.showFilterMenu()
        }

        rootPOV.closeBTN.setOnClickListener {
            listener.onFilterMenuCloseBTNClicked()
        }

        viewModel.items.observe(this, Observer {
            (rootPOV.filterItemsRCY.adapter as FilterItemsAdapter).items = it?: listOf()
        })

        return rootPOV
    }

    override fun enableItemWithIndex(i: Int) {
        viewModel.onItemSelectedWithIndex(i)
    }
}