package com.omid.musicplayer.activities.mainActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.util.configuration.AppConfiguration
import com.omid.musicplayer.util.sendData.ISend

class SongListSlidingUpPanelAdapter() : RecyclerView.Adapter<SongListSlidingUpPanelVH>() {
    private lateinit var songList: List<LatestMp3>
    private lateinit var iSend: ISend

    constructor(songList: List<LatestMp3>, iSend: ISend) : this() {
        this.songList = songList
        this.iSend = iSend
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
            clSong.setOnClickListener {
                iSend.onSongClick(info, songList)
            }
        }
    }
}