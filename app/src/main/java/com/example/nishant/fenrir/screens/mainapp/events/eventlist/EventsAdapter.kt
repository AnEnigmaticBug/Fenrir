package com.example.nishant.fenrir.screens.mainapp.events.eventlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.row_events_rcy.view.*

class EventsAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<EventsAdapter.EventVHolder>() {

    interface ClickListener {

        fun showDetailsForEventWithId(id: String)
    }

    var events = listOf<RawEvent>()
        set(value) {
            notifyItemRangeRemoved(0, field.size)
            field = value
            notifyItemRangeInserted(0, field.size)
        }

    override fun getItemCount() = events.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventVHolder(inflater.inflate(R.layout.row_events_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: EventVHolder, position: Int) {
        val event = events[position]

        holder.nameLBL.text = event.name
        holder.venueLBL.text = event.venue
        holder.timeLBL.text = event.time

        when(event.subscribed) {
            true  -> holder.alarmBellIMG.visibility = View.VISIBLE
            false -> holder.alarmBellIMG.visibility = View.INVISIBLE
        }

        holder.rootPOV.setOnClickListener {
            clickListener.showDetailsForEventWithId(event.id)
        }
    }

    class EventVHolder(val rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
        val venueLBL: TextView = rootPOV.venueLBL
        val timeLBL: TextView = rootPOV.timeLBL
        val alarmBellIMG: ImageView = rootPOV.alarmBellIMG
    }
}