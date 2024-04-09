package com.omid.musicplayer.fragments.specialSongsMoreFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class SpecialSongsMoreVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cvSpecialSongs = itemView.findViewById<CardView>(R.id.cv_special_songs)!!
    val imgSpecialSong = itemView.findViewById<AppCompatImageView>(R.id.img_special_song)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
    val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
}