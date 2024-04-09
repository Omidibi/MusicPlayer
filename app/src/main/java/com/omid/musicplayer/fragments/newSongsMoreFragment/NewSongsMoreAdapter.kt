package com.omid.musicplayer.fragments.newSongsMoreFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class NewSongsMoreAdapter(private val newSongs: List<LatestMp3>, private val iSelected : IOnSongClickListener): RecyclerView.Adapter<NewSongsMoreVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewSongsMoreVH {
        return NewSongsMoreVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.new_songs_more_row,null))
    }

    override fun getItemCount(): Int {
        return newSongs.size
    }

    override fun onBindViewHolder(holder: NewSongsMoreVH, position: Int) {
        holder.apply {
            val newSongsInfo = newSongs[position]
            artistName.text = newSongsInfo.mp3Artist
            songName.text = newSongsInfo.mp3Title
            Glide.with(AppConfiguration.getContext()).load(newSongsInfo.mp3ThumbnailB).into(imgNewSong)
            cvNewSongs.setOnClickListener {
                iSelected.onSongClick(newSongsInfo,newSongs)
            }
        }
    }
}