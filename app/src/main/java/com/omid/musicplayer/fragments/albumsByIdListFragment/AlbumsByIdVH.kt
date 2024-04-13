package com.omid.musicplayer.fragments.albumsByIdListFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class AlbumsByIdVH(itemView: View) : RecyclerView.ViewHolder(itemView){
    val imgAlbum = itemView.findViewById<AppCompatImageView>(R.id.img_album)!!
    val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
    val cvAlbumsByIdList = itemView.findViewById<CardView>(R.id.cv_albums_by_id_list)!!
}