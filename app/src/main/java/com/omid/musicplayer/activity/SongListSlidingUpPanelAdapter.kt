package com.omid.musicplayer.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class SongListSlidingUpPanelAdapter() : RecyclerView.Adapter<SongListSlidingUpPanelVH>() {

    private lateinit var songList: List<LatestMp3>
    private lateinit var iSelected: IOnSongClickListener

    constructor(songList: List<LatestMp3>, iSelected: IOnSongClickListener) : this() {
        this.songList = songList
        this.iSelected = iSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListSlidingUpPanelVH {
        return SongListSlidingUpPanelVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.song_list_row, null))
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: SongListSlidingUpPanelVH, position: Int) {
        holder.apply {
            val info = songList[position]
            Glide.with(AppConfiguration.getContext()).load(info.mp3ThumbnailB)
                .circleCrop()
                .into(imgSong)
            songName.text = info.mp3Title
            singerName.text = info.mp3Artist
            songDuration.text = info.mp3Duration

            cvSong.setOnClickListener {
                iSelected.onSongClick(info, songList)
            }
        }
    }
}