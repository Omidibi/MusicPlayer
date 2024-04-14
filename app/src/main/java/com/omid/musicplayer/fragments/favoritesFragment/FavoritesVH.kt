package com.omid.musicplayer.fragments.favoritesFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class FavoritesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cvFvt = itemView.findViewById<CardView>(R.id.cv_fvt)!!
    val imgFvt = itemView.findViewById<AppCompatImageView>(R.id.img_fvt)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
    val popupFvt = itemView.findViewById<AppCompatImageView>(R.id.popup_fvt)!!
}