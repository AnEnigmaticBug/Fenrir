package com.example.nishant.fenrir.screens.wallet.stalls

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_stalls.view.*

class StallsFragment : Fragment(), StallsAdapter.ClickListener {

    private lateinit var viewModel: StallsViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, StallsViewModelFactory())[StallsViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_stalls, container, false)
        
        rootPOV.stallsRCY.adapter = StallsAdapter(this)

        viewModel.rawStalls.observe(this, Observer {
            (rootPOV.stallsRCY.adapter as StallsAdapter).stalls = it?: listOf()
        })

        return rootPOV
    }

    override fun showItemsForStallWithIdAndName(id: String, name: String) {
        val bundle = Bundle().also {
            it.putString("stallId", id)
            it.putString("stallName", name)
        }
        rootPOV.findNavController().navigate(R.id.action_stallsFragment_to_itemsFragment, bundle)
    }
}