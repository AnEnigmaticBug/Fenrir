package com.example.nishant.fenrir.screens.mainapp.more.n2ovoting

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.domain.mainapp.Comedian
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.row_comedians_rcy.view.*

class ComediansAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<ComediansAdapter.ComedianVHolder>() {

    interface ClickListener {

        fun onVoteBTNPressed(name: String)
    }

    var comedians = listOf<Comedian>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = comedians.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComedianVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ComedianVHolder(inflater.inflate(R.layout.row_comedians_rcy, parent, false))
    }

    override fun onBindViewHolder(holder: ComedianVHolder, position: Int) {
        val comedian = comedians[position]

        Glide.with(holder.profilePicIMG.context)
                .load(comedian.profilePicURL)
                .apply(RequestOptions().placeholder(ContextCompat.getDrawable(holder.profilePicIMG.context, R.drawable.ic_profile_placeholder_160dp)))
                .into(holder.profilePicIMG)

        holder.nameLBL.text = comedian.name

        when(comedian.hasVote) {
            true -> holder.voteBTN.setImageResource(R.drawable.ic_voted_32dp)
            else -> holder.voteBTN.setImageResource(R.drawable.ic_not_voted_32dp)
        }

        holder.voteBTN.setOnClickListener {
            clickListener.onVoteBTNPressed(comedian.name)
        }
    }

    class ComedianVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val profilePicIMG: CircleImageView = rootPOV.profilePicIMG
        val nameLBL: TextView = rootPOV.nameLBL
        val voteBTN: ImageView = rootPOV.voteBTN
    }
}