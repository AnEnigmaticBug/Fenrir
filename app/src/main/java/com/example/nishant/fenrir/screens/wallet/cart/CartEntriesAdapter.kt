package com.example.nishant.fenrir.screens.wallet.cart

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.row_cart_entries_rcy.view.*

class CartEntriesAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<CartEntriesAdapter.CartEntryVHolder>() {

    interface ClickListener {

        fun onOrderRemoveButtonClicked(orderId: String)
    }

    var entries = listOf<RawCartEntry>()
        set(value) {
            notifyItemRangeRemoved(0, field.size)
            field = value
            notifyItemRangeInserted(0, field.size)
        }

    override fun getItemCount() = entries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartEntryVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CartEntryVHolder(inflater.inflate(R.layout.row_cart_entries_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: CartEntryVHolder, position: Int) {
        val entry = entries[position]

        holder.nameLBL.text = entry.name
        holder.stallNameLBL.text = entry.stallName
        holder.priceAndQuantityLBL.text = entry.priceAndQuantity
        holder.errorIMG.visibility = when(entry.isValid) {
            true  -> View.GONE
            false -> View.VISIBLE
        }

        holder.removeBTN.setOnClickListener {
            clickListener.onOrderRemoveButtonClicked(entry.id)
        }
    }

    class CartEntryVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
        val stallNameLBL: TextView = rootPOV.stallNameLBL
        val priceAndQuantityLBL: TextView = rootPOV.priceAndQuantityLBL
        val errorIMG: ImageView = rootPOV.errorIMG
        val removeBTN: ImageView = rootPOV.removeBTN
    }
}