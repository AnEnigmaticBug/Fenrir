package com.example.nishant.fenrir.screens.wallet.tracking.orderlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.row_orders_rcy.view.*

class OrdersAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    interface ClickListener {

        fun showTrackedEntriesForOrder(orderId: String, orderNo: String)
    }

    var orders = listOf<RawOrder>()
        set(value) {
            notifyItemRangeRemoved(0, field.size)
            field = value
            notifyItemRangeInserted(0, field.size)
        }

    override fun getItemCount() = orders.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return OrderViewHolder(inflater.inflate(R.layout.row_orders_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.nameLBL.text = order.name
        holder.dateAndTimeLBL.text = order.dateAndTime

        holder.rootPOV.setOnClickListener {
            clickListener.showTrackedEntriesForOrder(order.id, order.name.split("Order Number ")[1])
        }
    }

    class OrderViewHolder(val rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
        val dateAndTimeLBL: TextView = rootPOV.dateAndTimeLBL
    }
}