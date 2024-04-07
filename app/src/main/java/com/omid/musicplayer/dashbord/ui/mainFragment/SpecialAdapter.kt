package com.omid.musicplayer.dashbord.ui.mainFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.util.configuration.AppConfiguration
import com.omid.musicplayer.util.sendData.IOnSongClickListener

class SpecialAdapter(private val specialSongs: List<LatestMp3>, private val iSelected : IOnSongClickListener): RecyclerView.Adapter<SpecialVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialVH {
        return SpecialVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.special_song_row,null))
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
                iSelected.onSongClick(specialSongInfo,specialSongs)
            }
        }
    }
}