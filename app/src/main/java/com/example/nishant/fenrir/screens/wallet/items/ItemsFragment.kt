package com.example.nishant.fenrir.screens.wallet.items

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_items.view.*

class ItemsFragment : Fragment(), ItemsAdapter.ClickListener {

    private lateinit var viewModel: ItemsViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val id = arguments!!.getString("stallId")
        val name = arguments!!.getString("stallName")
        viewModel = ViewModelProviders.of(this, ItemsViewModelFactory(id))[ItemsViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_items, container, false)

        rootPOV.stallNameLBL.text = name

        rootPOV.backBTN.setOnClickListener {
            rootPOV.findNavController().navigateUp()
        }

        rootPOV.itemsRCY.adapter = ItemsAdapter(this)

        viewModel.rawItems.observe(this, Observer {
            (rootPOV.itemsRCY.adapter as ItemsAdapter).items = it?: listOf()
        })

        return rootPOV
    }

    override fun showOverlayForItemWithId(id: String) {

    }
}