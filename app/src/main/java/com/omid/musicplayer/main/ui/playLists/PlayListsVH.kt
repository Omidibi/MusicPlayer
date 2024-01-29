package com.omid.musicplayer.main.ui.playLists

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class PlayListsVH(itemView: View) : RecyclerView.ViewHolder(itemView){
    val cvPlaylists = itemView.findViewById<CardView>(R.id.cv_playlists)!!
    val ivPlaylists = itemView.findViewById<AppCompatImageView>(R.id.iv_playlists)!!
    val tvNamePlaylists = itemView.findViewById<AppCompatTextView>(R.id.tv_name_playlists)!!
}