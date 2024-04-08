package com.omid.musicplayer.ui.dashboard.mainFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class SpecialVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cvSpecialSongs = itemView.findViewById<CardView>(R.id.cv_special_songs)!!
    val ivSpecialSongs = itemView.findViewById<AppCompatImageView>(R.id.iv_special_songs)!!
    val musicName = itemView.findViewById<AppCompatTextView>(R.id.music_name)!!
    val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
}