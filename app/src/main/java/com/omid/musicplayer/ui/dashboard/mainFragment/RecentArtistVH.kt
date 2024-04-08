package com.omid.musicplayer.ui.dashboard.mainFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class RecentArtistVH(itemView: View) : RecyclerView.ViewHolder(itemView){
    val clRecentArtist = itemView.findViewById<ConstraintLayout>(R.id.cl_recent_artist)!!
    val ivRecentArtist = itemView.findViewById<AppCompatImageView>(R.id.iv_recent_artist)!!
    val artistNameRecent = itemView.findViewById<AppCompatTextView>(R.id.artist_name_recent)!!
}