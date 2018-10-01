package com.example.nishant.fenrir.screens.wallet.cart

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.fra_cart.view.*

class CartFragment : Fragment(), CartEntriesAdapter.ClickListener {

    private lateinit var viewModel: CartViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, CartViewModelFactory())[CartViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_cart, container, false)

        rootPOV.cartEntriesRCY.adapter = CartEntriesAdapter(this)

        rootPOV.payBTN.setOnClickListener {
            viewModel.onPayButtonClicked()
        }

        rootPOV.trackBTN.setOnClickListener {
            rootPOV.findNavController().navigate(R.id.action_cartFragment_to_orderListFragment)
        }

        viewModel.rawOrders.observe(this, Observer {
            (rootPOV.cartEntriesRCY.adapter as CartEntriesAdapter).entries = it?: listOf()
        })

        viewModel.totalCost.observe(this, Observer {
            rootPOV.totalAmountLBL.text = it?: "Total Amount: INR 0"
        })

        viewModel.buyStatus.observe(this, Observer {
            when(it!!) {
                is BuyAttemptStatus.Idle       -> {
                    showInProgressStuff(false)
                }
                is BuyAttemptStatus.InProgress -> {
                    showInProgressStuff(true)
                }
                is BuyAttemptStatus.Success    -> {
                    Toast.makeText(this.requireActivity(), "Order placed successfully", Toast.LENGTH_LONG).show()
                }
                is BuyAttemptStatus.Failure    -> {
                    Toast.makeText(this.requireActivity(), (it as BuyAttemptStatus.Failure).message , Toast.LENGTH_LONG).show()
                }
            }
        })

        return rootPOV
    }

    private fun showInProgressStuff(show: Boolean) {
        val visibility = when(show) {
            true  -> View.VISIBLE
            false -> View.GONE
        }
        rootPOV.screenFaderPOV.visibility = visibility
        rootPOV.inProgressLOT.visibility = visibility
    }

    override fun onOrderRemoveButtonClicked(orderId: String) {
        viewModel.onOrderRemoved(orderId)
    }
}