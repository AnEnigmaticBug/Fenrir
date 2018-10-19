package com.example.nishant.fenrir.screens.wallet.money

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.navigation.NavHostFragment
import com.example.nishant.fenrir.navigation.NavigationGraph
import kotlinx.android.synthetic.main.fra_money.view.*

class MoneyFragment : NavHostFragment() {

    private lateinit var viewModel: MoneyViewModel
    private lateinit var rootPOV: View

    override val navViewId = R.id.overlayHostFRM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, MoneyViewModelFactory())[MoneyViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_money, container, false)

        rootPOV.addMoneyLBL.setOnClickListener {
            if(viewModel.isBITSian.value!!) {
                rootPOV.screenFaderPOV.visibility = View.VISIBLE
                show(NavigationGraph.Wallet.Money.ADD_MONEY, null)
            }
            Toast.makeText(requireContext(), "Only BITSians can add money through the app", Toast.LENGTH_SHORT).show()
        }

        rootPOV.screenFaderPOV.setOnClickListener {
            rootPOV.screenFaderPOV.visibility = View.GONE
            exit()
        }

        viewModel.userDetails.observe(this, Observer {
            rootPOV.nameLBL.text = it!!.name
            rootPOV.userIdLBL.text = it.userId
            Glide.with(this)
                    .load(it.profilePicURL)
                    .apply(RequestOptions().placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.ic_profile_placeholder_160dp)))
                    .into(rootPOV.profilePicIMG)
        })

        viewModel.balance.observe(this, Observer {
            rootPOV.balanceLBL.text = it
        })

        return rootPOV
    }

    override fun exit() {
        super.exit()
        rootPOV.screenFaderPOV.visibility = View.GONE
    }
}