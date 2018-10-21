package com.example.nishant.fenrir.screens.wallet.tracking.trackorder

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
import kotlinx.android.synthetic.main.fra_track_order.view.*

class TrackOrderFragment : Fragment(), TrackedEntriesAdapter.ClickListener {

    private lateinit var viewModel: TrackOrderViewModel
    private lateinit var rootPOV: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val orderId = arguments!!.getString("orderId")
        val orderNo = arguments!!.getString("orderNo")

        viewModel = ViewModelProviders.of(this, TrackOrderViewModelFactory(orderId))[TrackOrderViewModel::class.java]

        rootPOV = inflater.inflate(R.layout.fra_track_order, container, false)

        rootPOV.backBTN.setOnClickListener {
            rootPOV.findNavController().navigateUp()
        }

        rootPOV.orderNumberLBL.text = "Order Number $orderNo"

        rootPOV.trackedEntriesRCY.adapter = TrackedEntriesAdapter(this)

        viewModel.trackedEntries.observe(this, Observer {
            (rootPOV.trackedEntriesRCY.adapter as TrackedEntriesAdapter).trackedEntries = it?: listOf()
        })

        viewModel.status.observe(this, Observer {
            when(it!!) {
                is NotifyOTPStatus.Idle       -> {
                    showInProgressStuff(false)
                }
                is NotifyOTPStatus.InProgress -> {
                    showInProgressStuff(true)
                }
                is NotifyOTPStatus.Success    -> {
                    showInProgressStuff(false)
                }
                is NotifyOTPStatus.Failure    -> {
                    showInProgressStuff(false)
                    Toast.makeText(requireContext(), (it as NotifyOTPStatus.Failure).message, Toast.LENGTH_SHORT).show()
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

    override fun onOtpShown(id: String) {
        viewModel.notifyOTPShown(id)
    }
}