package com.omid.musicplayer.fragments.searchFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class SongSearchVH(itemView: View) : RecyclerView.ViewHolder(itemView){
    val cvSongSearch = itemView.findViewById<CardView>(R.id.cv_song_search)!!
    val ivSongSearch = itemView.findViewById<AppCompatImageView>(R.id.iv_song_search)!!
    val tvNameSong = itemView.findViewById<AppCompatTextView>(R.id.tv_name_song)!!
    val tvNameArtist = itemView.findViewById<AppCompatTextView>(R.id.tv_name_artist)!!
}