package com.omid.musicplayer.activities.albumsByIdListActivity

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class AlbumsByIdVH(itemView: View) : RecyclerView.ViewHolder(itemView){
    val imgAlbum = itemView.findViewById<AppCompatImageView>(R.id.img_album)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val singerName = itemView.findViewById<AppCompatTextView>(R.id.singer_name)!!
    val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
    val clAlbumsByIdList = itemView.findViewById<ConstraintLayout>(R.id.cl_albums_by_id_list)!!
}