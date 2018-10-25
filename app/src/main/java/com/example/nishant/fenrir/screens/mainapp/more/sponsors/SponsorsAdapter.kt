package com.example.nishant.fenrir.screens.mainapp.more.sponsors

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nishant.fenrir.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.row_sponsors_rcy.view.*


class SponsorsAdapter : RecyclerView.Adapter<SponsorsAdapter.SponsorsVHolder>() {

    var sponsors = listOf<Sponsor>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = sponsors.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorsVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SponsorsVHolder(inflater.inflate(R.layout.row_sponsors_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: SponsorsVHolder, position: Int) {
        val sponsor = sponsors[position]

        holder.nameLBL.text = sponsor.name
        holder.descriptionLBL.text = sponsor.description

        Glide.with(holder.profilePicIMG.context)
                .load(sponsor.profilePicURL)
                .apply(RequestOptions().placeholder(ContextCompat.getDrawable(holder.profilePicIMG.context, R.drawable.ic_profile_placeholder_160dp)))
                .into(holder.profilePicIMG)
    }

    class SponsorsVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
        val descriptionLBL: TextView = rootPOV.descriptionLBL
        val profilePicIMG: CircleImageView = rootPOV.profilePicIMG
    }
}