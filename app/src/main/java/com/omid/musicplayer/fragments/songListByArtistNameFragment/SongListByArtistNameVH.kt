package com.omid.musicplayer.fragments.songListByArtistNameFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class SongListByArtistNameVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val clArtistName = itemView.findViewById<ConstraintLayout>(R.id.cl_artist_name)!!
    val imgArtistName = itemView.findViewById<AppCompatImageView>(R.id.img_artist_name)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val singerName = itemView.findViewById<AppCompatTextView>(R.id.singer_name)!!
    val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
}