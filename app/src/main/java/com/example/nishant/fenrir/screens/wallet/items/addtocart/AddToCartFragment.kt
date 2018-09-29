package com.example.nishant.fenrir.screens.wallet.items.addtocart

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavigationHost
import kotlinx.android.synthetic.main.fra_add_to_cart.view.*

class AddToCartFragment : Fragment() {

    private lateinit var navigationHost: NavigationHost
    private lateinit var viewModel: AddToCartViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navigationHost = parentFragment as NavigationHost

        val stallId = arguments!!.getString("stallId")
        val itemId = arguments!!.getString("itemId")
        viewModel = ViewModelProviders.of(this, AddToCartViewModelFactory(stallId, itemId))[AddToCartViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_add_to_cart, container, false)

        rootPOV.closeBTN.setOnClickListener {
            navigationHost.exit()
        }

        rootPOV.incrementBTN.setOnClickListener {
            viewModel.incrementItemQuantity()
        }

        rootPOV.decrementBTN.setOnClickListener {
            viewModel.decrementItemQuantity()
        }

        rootPOV.addToCartBTN.setOnClickListener {
            viewModel.addItemToCart()
        }

        viewModel.itemName.observe(this, Observer {
            rootPOV.nameLBL.text = it.toString()
        })

        viewModel.quantity.observe(this, Observer {
            rootPOV.quantityLBL.text = it.toString()
        })

        viewModel.totalAmount.observe(this, Observer {
            rootPOV.totalAmountLBL.text = it.toString()
        })

        viewModel.messages.observe(this, Observer {
            if(it != null) {
                Toast.makeText(this.requireActivity(), it, Toast.LENGTH_SHORT).show()
                if(it.toUpperCase().contains("ADDED")) {
                    navigationHost.exit()
                }
            }
        })

        return rootPOV
    }
}