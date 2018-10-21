package com.example.nishant.fenrir.screens.wallet.tracking.trackorder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.domain.wallet.TrackingStatus
import kotlinx.android.synthetic.main.row_tracked_entries_rcy_entry.view.*
import kotlinx.android.synthetic.main.row_tracked_entries_rcy_stall.view.*

class TrackedEntriesAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ClickListener {

        fun onOtpShown(id: String)
    }

    var trackedEntries = listOf<TrackingScreenRow>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = trackedEntries.size

    override fun getItemViewType(position: Int): Int {
        return when(trackedEntries[position]) {
            is TrackingScreenRow.Stall -> 0
            is TrackingScreenRow.Entry -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            0    -> TrackingScreenRowVHolderStall(inflater.inflate(R.layout.row_tracked_entries_rcy_stall, parent, false))
            else -> TrackingScreenRowVHolderEntry(inflater.inflate(R.layout.row_tracked_entries_rcy_entry, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is TrackingScreenRowVHolderStall -> {
                val entry = trackedEntries[position] as TrackingScreenRow.Stall

                holder.otpLBL.visibility = View.GONE

                holder.stallNameLBL.text = entry.name
                if(entry.otpShown) {
                    holder.showOtpBTN.visibility = View.GONE
                    holder.otpLBL.visibility = View.VISIBLE
                    holder.otpLBL.text = "OTP: ${entry.otp}"
                }
                holder.showOtpBTN.setOnClickListener {
                    when(entry.otp) {
                        null -> Toast.makeText(holder.showOtpBTN.context, "Can't use OTP rn", Toast.LENGTH_SHORT).show()
                        else -> {
                            clickListener.onOtpShown(entry.entryId)
                        }
                    }
                }
            }
            is TrackingScreenRowVHolderEntry -> {
                val entry = trackedEntries[position] as TrackingScreenRow.Entry

                holder.itemNameLBL.text = entry.itemName
                holder.priceAndQuantityLBL.text = entry.priceAndQuantity

                when(entry.status) {
                    TrackingStatus.Pending   -> {
                        holder.statusLBL.apply {
                            text = "Pending"
                            setColorByResId(R.color.yel01)
                        }
                        holder.statusLBL.text = "Pending"
                        holder.redLightPOV.visibility = View.INVISIBLE
                        holder.ambLightPOV.visibility = View.VISIBLE
                        holder.grnLightPOV.visibility = View.INVISIBLE
                    }
                    TrackingStatus.Declined  -> {
                        holder.statusLBL.apply {
                            text = "Declined"
                            setColorByResId(R.color.red01)
                        }
                        holder.redLightPOV.visibility = View.VISIBLE
                        holder.ambLightPOV.visibility = View.INVISIBLE
                        holder.grnLightPOV.visibility = View.INVISIBLE
                    }
                    TrackingStatus.Accepted  -> {
                        holder.statusLBL.apply {
                            text = "Accepted"
                            setColorByResId(R.color.grn01)
                        }
                        holder.redLightPOV.visibility = View.INVISIBLE
                        holder.ambLightPOV.visibility = View.INVISIBLE
                        holder.grnLightPOV.visibility = View.VISIBLE
                    }
                    TrackingStatus.Ready ->     {
                        holder.statusLBL.apply {
                            text = "Ready"
                            setColorByResId(R.color.blk01)
                        }
                        holder.redLightPOV.visibility = View.VISIBLE
                        holder.ambLightPOV.visibility = View.VISIBLE
                        holder.grnLightPOV.visibility = View.VISIBLE
                    }
                    TrackingStatus.Finished  -> {
                        holder.statusLBL.apply {
                            text = "Finished"
                            setColorByResId(R.color.gry04)
                        }
                        holder.redLightPOV.visibility = View.INVISIBLE
                        holder.ambLightPOV.visibility = View.INVISIBLE
                        holder.grnLightPOV.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun TextView.setColorByResId(resId: Int) {
        this.setTextColor(resources.getColor(resId))
    }

    class TrackingScreenRowVHolderEntry(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val itemNameLBL: TextView = rootPOV.itemNameLBL
        val priceAndQuantityLBL: TextView = rootPOV.priceAndQuantityLBL

        val redLightPOV: View = rootPOV.redLightPOV
        val ambLightPOV: View = rootPOV.ambLightPOV
        val grnLightPOV: View = rootPOV.grnLightPOV

        val statusLBL: TextView = rootPOV.statusLBL
    }

    class TrackingScreenRowVHolderStall(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val stallNameLBL: TextView = rootPOV.stallNameLBL
        val otpLBL: TextView = rootPOV.otpLBL
        val showOtpBTN: TextView = rootPOV.showOtpBTN
    }
}