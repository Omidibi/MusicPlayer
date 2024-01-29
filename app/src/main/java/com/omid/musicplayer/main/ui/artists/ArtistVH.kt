package com.omid.musicplayer.main.ui.artists

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class ArtistVH(itemView: View) : RecyclerView.ViewHolder(itemView){
    val cvArtist = itemView.findViewById<CardView>(R.id.cv_artist)!!
    val ivArtist = itemView.findViewById<AppCompatImageView>(R.id.iv_artist)!!
    val tvNameArtist = itemView.findViewById<AppCompatTextView>(R.id.tv_name_artist)!!
    val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
    val aboutSinger = itemView.findViewById<AppCompatImageView>(R.id.about_singer)!!
}