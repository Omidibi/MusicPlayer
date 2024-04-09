package com.omid.musicplayer.fragments.newSongsMoreFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class NewSongsMoreVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cvNewSongs = itemView.findViewById<CardView>(R.id.cv_new_songs)!!
    val imgNewSong = itemView.findViewById<AppCompatImageView>(R.id.img_new_song)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
    val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
}