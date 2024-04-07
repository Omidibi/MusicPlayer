package com.omid.musicplayer.dashbord.ui.albums

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class AlbumsListVH(itemView: View) : RecyclerView.ViewHolder(itemView){
    val cvAlbums = itemView.findViewById<CardView>(R.id.cv_albums)!!
    val ivAlbums = itemView.findViewById<AppCompatImageView>(R.id.iv_albums)!!
    val tvNameAlbums = itemView.findViewById<AppCompatTextView>(R.id.tv_name_albums)!!
    val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!

}