package com.omid.musicplayer.main.ui.mainFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class NewSongsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cvNewSongs = itemView.findViewById<CardView>(R.id.cv_new_songs)!!
    val ivNewSongs = itemView.findViewById<AppCompatImageView>(R.id.iv_new_songs)!!
    val musicName = itemView.findViewById<AppCompatTextView>(R.id.music_name)!!
    val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
}