package com.omid.musicplayer.dashbord.ui.mainFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class LatestSongsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
    val musicName = itemView.findViewById<AppCompatTextView>(R.id.music_name)!!
    val ivLatestSongs = itemView.findViewById<AppCompatImageView>(R.id.iv_latest_songs)!!
    val cvLatestSongs = itemView.findViewById<CardView>(R.id.cv_latest_songs)!!

}