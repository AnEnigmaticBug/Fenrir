package com.example.nishant.fenrir.screens.wallet.tracking.orderlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_order_list.view.*

class OrderListFragment : Fragment(), OrdersAdapter.ClickListener {

    private lateinit var viewModel: OrderListViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, OrderListViewModelFactory())[OrderListViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_order_list, container, false)

        rootPOV.ordersRCY.adapter = OrdersAdapter(this)

        viewModel.orders.observe(this, Observer {
            (rootPOV.ordersRCY.adapter as OrdersAdapter).orders = it?: listOf()
        })

        return rootPOV
    }

    override fun showTrackedEntriesForOrder(orderId: String, orderNo: String) {
    }
}