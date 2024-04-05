package com.omid.musicplayer.main.ui.mainFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class RecentArtistVH(itemView: View) : RecyclerView.ViewHolder(itemView){
    val cvRecentArtist = itemView.findViewById<CardView>(R.id.cv_recent_artist)!!
    val ivRecentArtist = itemView.findViewById<AppCompatImageView>(R.id.iv_recent_artist)!!
    val artistNameRecent = itemView.findViewById<AppCompatTextView>(R.id.artist_name_recent)!!
}