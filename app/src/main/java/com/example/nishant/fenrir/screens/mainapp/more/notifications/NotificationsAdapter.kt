package com.example.nishant.fenrir.screens.mainapp.more.notifications

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.row_notifications_rcy.view.*

class NotificationsAdapter : RecyclerView.Adapter<NotificationsAdapter.NotificationVHolder>() {

    var notifications = listOf<RawPayloadedNotification>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = notifications.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotificationVHolder(inflater.inflate(R.layout.row_notifications_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: NotificationVHolder, position: Int) {
        val notification = notifications[position]

        holder.titleLBL.text = notification.title
        holder.datetimeLBL.text = notification.datetime
        holder.bodyLBL.text = notification.body
    }

    class NotificationVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val titleLBL: TextView = rootPOV.titleLBL
        val datetimeLBL: TextView = rootPOV.datetimeLBL
        val bodyLBL: TextView = rootPOV.bodyLBL
    }
}