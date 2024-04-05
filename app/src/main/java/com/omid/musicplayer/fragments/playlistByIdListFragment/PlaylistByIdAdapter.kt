package com.omid.musicplayer.fragments.playlistByIdListFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.Songs
import com.omid.musicplayer.util.configuration.AppConfiguration

class PlaylistByIdAdapter(private val playlistByIdMp3: List<Songs>): RecyclerView.Adapter<PlaylistByIdVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistByIdVH {
        val view = LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.playlist_by_id_list_row,null)
        return PlaylistByIdVH(view)
    }

    override fun getItemCount(): Int {
        return playlistByIdMp3.size
    }

    override fun onBindViewHolder(holder: PlaylistByIdVH, position: Int) {
        holder.apply {
            val playlistByIdInfo = playlistByIdMp3[position]
            Glide.with(AppConfiguration.getContext()).load(playlistByIdInfo.mp3ThumbnailB).into(imgPlaylist)
            songName.text = playlistByIdInfo.mp3Title
            singerName.text = playlistByIdInfo.mp3Artist
        }
    }
}