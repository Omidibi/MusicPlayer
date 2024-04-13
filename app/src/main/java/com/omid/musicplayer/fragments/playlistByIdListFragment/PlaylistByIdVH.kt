package com.omid.musicplayer.fragments.playlistByIdListFragment

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class PlaylistByIdVH(itemView: View) : RecyclerView.ViewHolder(itemView){
    val cvPlaylistByIdList = itemView.findViewById<CardView>(R.id.cv_playlist_by_id_list)!!
    val imgPlaylist = itemView.findViewById<AppCompatImageView>(R.id.img_playlist)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
    val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
}