package com.example.nishant.fenrir.screens.events.eventfilter.eventfilterlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.row_filter_items_rcy.view.*

class FilterItemsAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<FilterItemsAdapter.FilterItemVHolder>() {

    interface ClickListener {

        fun enableItemWithIndex(i: Int)
    }

    var items = listOf<RawListItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FilterItemVHolder(inflater.inflate(R.layout.row_filter_items_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: FilterItemVHolder, position: Int) {
        val item = items[position]
        holder.itemBTN.text = item.content
        holder.itemBTN.setActiveStyle(item.enabled)

        holder.itemBTN.setOnClickListener {
            clickListener.enableItemWithIndex(item.index)
        }
    }

    private fun TextView.setActiveStyle(value: Boolean) {
        when(value) {
            true  -> {
                this.setTextColor(resources.getColor(R.color.red01))
                this.setBackgroundResource(R.drawable.sh_capsule_wht01)
            }
            false -> {
                this.setTextColor(resources.getColor(R.color.wht01))
                this.setBackgroundResource(R.drawable.sh_capsule_border)
            }
        }
    }

    class FilterItemVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val itemBTN: TextView = rootPOV.itemBTN
    }
}