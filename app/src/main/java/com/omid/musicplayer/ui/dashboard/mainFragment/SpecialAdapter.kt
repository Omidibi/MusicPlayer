package com.omid.musicplayer.ui.dashboard.mainFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class SpecialAdapter(private val specialSongs: List<LatestMp3>, private val iSelected: IOnSongClickListener) : RecyclerView.Adapter<SpecialAdapter.SpecialVH>() {

    inner class SpecialVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvSpecialSongs = itemView.findViewById<CardView>(R.id.cv_special_songs)!!
        val ivSpecialSongs = itemView.findViewById<AppCompatImageView>(R.id.iv_special_songs)!!
        val musicName = itemView.findViewById<AppCompatTextView>(R.id.music_name)!!
        val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialVH {
        return SpecialVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.special_song_row, null))
    }

    override fun getItemCount(): Int {
        return specialSongs.size
    }

    override fun onBindViewHolder(holder: SpecialVH, position: Int) {
        holder.apply {
            val specialSongInfo = specialSongs[position]
            artistName.text = specialSongInfo.mp3Artist
            musicName.text = specialSongInfo.mp3Title
            Glide.with(AppConfiguration.getContext()).load(specialSongInfo.mp3ThumbnailB).into(ivSpecialSongs)
            cvSpecialSongs.setOnClickListener {
                iSelected.onSongClick(specialSongInfo, specialSongs)
            }
        }
    }
}