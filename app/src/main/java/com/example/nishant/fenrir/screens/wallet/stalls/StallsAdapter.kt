package com.example.nishant.fenrir.screens.wallet.stalls

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.row_stalls_rcy.view.*

class StallsAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<StallsAdapter.StallVHolder>() {

    interface ClickListener {

        fun showItemsForStallWithIdAndName(id: String, name: String)
    }

    var stalls = listOf<RawStall>()
        set(value) {
            notifyItemRangeRemoved(0, field.size)
            field = value
            notifyItemRangeInserted(0, field.size)
        }

    override fun getItemCount() = stalls.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StallVHolder(inflater.inflate(R.layout.row_stalls_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: StallVHolder, position: Int) {
        val stall = stalls[position]

        holder.nameLBL.text = stall.name

        holder.rootPOV.setOnClickListener {
            clickListener.showItemsForStallWithIdAndName(stall.id, stall.name)
        }
    }

    class StallVHolder(val rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
    }
}