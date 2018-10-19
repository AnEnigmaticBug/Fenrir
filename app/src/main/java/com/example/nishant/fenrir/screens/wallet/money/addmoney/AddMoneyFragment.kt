package com.example.nishant.fenrir.screens.wallet.money.addmoney

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
import kotlinx.android.synthetic.main.fra_add_money.view.*

class AddMoneyFragment : Fragment() {

    private lateinit var navigationHost: NavigationHost
    private lateinit var viewModel: AddMoneyViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navigationHost = parentFragment as NavigationHost

        viewModel = ViewModelProviders.of(this, AddMoneyViewModelFactory())[AddMoneyViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_add_money, container, false)

        rootPOV.addMoneyBTN.setOnClickListener {
            viewModel.addMoney(rootPOV.quantityTXT.text.toString().toInt())
        }

        viewModel.addMoneyStatus.observe(this, Observer {
            when(it!!) {
                is AddMoneyAttemptStatus.Success -> Toast.makeText(requireContext(), "Money added successfully", Toast.LENGTH_SHORT).show()
                is AddMoneyAttemptStatus.Failure -> Toast.makeText(requireContext(), (it as AddMoneyAttemptStatus.Failure).message, Toast.LENGTH_SHORT).show()
            }
            navigationHost.exit()
        })

        return rootPOV
    }
}