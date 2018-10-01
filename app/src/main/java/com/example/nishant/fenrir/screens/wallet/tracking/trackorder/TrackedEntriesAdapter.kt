package com.example.nishant.fenrir.screens.wallet.tracking.trackorder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.domain.wallet.TrackingStatus
import kotlinx.android.synthetic.main.row_tracked_entries_rcy.view.*

class TrackedEntriesAdapter : RecyclerView.Adapter<TrackedEntriesAdapter.TrackedEntryVHolder>() {

    var trackedEntries = listOf<RawTrackedEntry>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = trackedEntries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackedEntryVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TrackedEntryVHolder(inflater.inflate(R.layout.row_tracked_entries_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: TrackedEntryVHolder, position: Int) {
        val entry = trackedEntries[position]

        holder.itemNameLBL.text = entry.itemName
        holder.stallNameLBL.text = entry.stallName
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
            TrackingStatus.Delivered -> {
                holder.statusLBL.apply {
                    text = "Delivered"
                    setColorByResId(R.color.blk01)
                }
                holder.redLightPOV.visibility = View.VISIBLE
                holder.ambLightPOV.visibility = View.VISIBLE
                holder.grnLightPOV.visibility = View.VISIBLE
            }
        }
    }

    private fun TextView.setColorByResId(resId: Int) {
        this.setTextColor(resources.getColor(resId))
    }

    class TrackedEntryVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val itemNameLBL: TextView = rootPOV.itemNameLBL
        val stallNameLBL: TextView = rootPOV.stallNameLBL
        val priceAndQuantityLBL: TextView = rootPOV.priceAndQuantityLBL

        val redLightPOV: View = rootPOV.redLightPOV
        val ambLightPOV: View = rootPOV.ambLightPOV
        val grnLightPOV: View = rootPOV.grnLightPOV

        val statusLBL: TextView = rootPOV.statusLBL
    }
}