package com.example.nishant.fenrir.screens.wallet.items

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.row_items_rcy.view.*

class ItemsAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<ItemsAdapter.ItemVHolder>() {

    interface ClickListener {

        fun showOverlayForItemWithId(id: String)
    }

    var items = listOf<RawItem>()
        set(value) {
            notifyItemRangeRemoved(0, field.size)
            field = value
            notifyItemRangeInserted(0, field.size)
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemVHolder(inflater.inflate(R.layout.row_items_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: ItemVHolder, position: Int) {
        val item = items[position]

        holder.nameLBL.text = item.name
        holder.priceLBL.text = item.price

        holder.addBTN.setOnClickListener {
            clickListener.showOverlayForItemWithId(item.id)
        }
    }

    class ItemVHolder(val rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
        val priceLBL: TextView = rootPOV.priceLBL
        val addBTN: ImageView = rootPOV.addBTN
    }
}