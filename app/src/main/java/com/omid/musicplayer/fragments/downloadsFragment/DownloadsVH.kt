package com.omid.musicplayer.fragments.downloadsFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class DownloadsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cvDownload = itemView.findViewById<CardView>(R.id.cv_download)!!
    val imgDownload = itemView.findViewById<AppCompatImageView>(R.id.img_download)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
    val popupDownload = itemView.findViewById<AppCompatImageView>(R.id.popup_download)!!
}