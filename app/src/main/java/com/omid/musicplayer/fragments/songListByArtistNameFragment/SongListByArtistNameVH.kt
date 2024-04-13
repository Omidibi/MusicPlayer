package com.omid.musicplayer.fragments.songListByArtistNameFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class SongListByArtistNameVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cvArtistSongs = itemView.findViewById<CardView>(R.id.cv_artist_songs)!!
    val imgArtistName = itemView.findViewById<AppCompatImageView>(R.id.img_artist_name)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
    val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
}