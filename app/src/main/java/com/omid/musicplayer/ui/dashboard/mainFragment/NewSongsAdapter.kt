package com.omid.musicplayer.ui.dashboard.mainFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class NewSongsAdapter(private val newSongs: List<LatestMp3>, private val iSelected : IOnSongClickListener): RecyclerView.Adapter<NewSongsVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewSongsVH {
        return NewSongsVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.new_songs_row,null))
    }

    override fun getItemCount(): Int {
        return newSongs.size
    }

    override fun onBindViewHolder(holder: NewSongsVH, position: Int) {
        holder.apply {
            val newSongsInfo = newSongs[position]
            artistName.text = newSongsInfo.mp3Artist
            musicName.text = newSongsInfo.mp3Title
            Glide.with(AppConfiguration.getContext()).load(newSongsInfo.mp3ThumbnailB).into(ivNewSongs)
            cvNewSongs.setOnClickListener {
                iSelected.onSongClick(newSongsInfo,newSongs)
            }
        }
    }
}