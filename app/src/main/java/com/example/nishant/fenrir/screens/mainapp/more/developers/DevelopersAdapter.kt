package com.example.nishant.fenrir.screens.mainapp.more.developers

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
import kotlinx.android.synthetic.main.row_developers_rcy.view.*

class DevelopersAdapter : RecyclerView.Adapter<DevelopersAdapter.DevelopersVHolder>() {

    var developers = listOf<Developer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = developers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevelopersVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DevelopersVHolder(inflater.inflate(R.layout.row_developers_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: DevelopersVHolder, position: Int) {
        val developer = developers[position]

        holder.nameLBL.text = developer.name
        holder.descriptionLBL.text = developer.description

        Glide.with(holder.profilePicIMG.context)
                .load(developer.profilePicURL)
                .apply(RequestOptions().placeholder(ContextCompat.getDrawable(holder.profilePicIMG.context, R.drawable.ic_profile_placeholder_160dp)))
                .into(holder.profilePicIMG)
    }

    class DevelopersVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
        val descriptionLBL: TextView = rootPOV.descriptionLBL
        val profilePicIMG: CircleImageView = rootPOV.profilePicIMG
    }
}