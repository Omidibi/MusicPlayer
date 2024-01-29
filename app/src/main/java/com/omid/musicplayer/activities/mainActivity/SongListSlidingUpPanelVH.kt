package com.omid.musicplayer.activities.mainActivity

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class SongListSlidingUpPanelVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val clSong = itemView.findViewById<ConstraintLayout>(R.id.cl_song)!!
    val imgSong = itemView.findViewById<AppCompatImageView>(R.id.img_song)!!
    val singerName = itemView.findViewById<AppCompatTextView>(R.id.singer_name)!!
    val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
    val songDuration = itemView.findViewById<AppCompatTextView>(R.id.song_duration)!!
}