package com.example.nishant.fenrir.screens.mainapp.profile.signedeventlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nishant.fenrir.R
import kotlinx.android.synthetic.main.row_signed_events_rcy.view.*

class SignedEventsAdapter : RecyclerView.Adapter<SignedEventsAdapter.SignedEventVHolder>() {

    var events = listOf<RawSignedEvent>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = events.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignedEventVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SignedEventVHolder(inflater.inflate(R.layout.row_signed_events_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: SignedEventVHolder, position: Int) {
        val event = events[position]

        holder.nameLBL.text = event.name
        holder.numberOfTicketsLBL.text = event.numberOfTickets.toString()
    }

    class SignedEventVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
        val numberOfTicketsLBL: TextView = rootPOV.numberOfTicketsLBL
    }
}