package com.omid.musicplayer.activity

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class SongListSlidingUpPanelVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cvSong = itemView.findViewById<CardView>(R.id.cv_song)!!
    val imgSong = itemView.findViewById<AppCompatImageView>(R.id.img_song)!!
    val imgEq = itemView.findViewById<AppCompatImageView>(R.id.img_eq)!!
    val singerName = itemView.findViewById<AppCompatTextView>(R.id.singer_name)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val songDuration = itemView.findViewById<AppCompatTextView>(R.id.song_duration)!!
}