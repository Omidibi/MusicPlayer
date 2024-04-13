package com.omid.musicplayer.fragments.songListByArtistNameFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class SongListByArtistNameAdapter(private val listByArtistName : List<LatestMp3>,private val iSelected: IOnSongClickListener): RecyclerView.Adapter<SongListByArtistNameVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListByArtistNameVH {
        return SongListByArtistNameVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.list_by_artistname_row,null))
    }

    override fun getItemCount(): Int {
        return listByArtistName.size
    }

    override fun onBindViewHolder(holder: SongListByArtistNameVH, position: Int) {
        holder.apply {
            val listByArtistNameInfo = listByArtistName[position]
            Glide.with(AppConfiguration.getContext()).load(listByArtistNameInfo.mp3ThumbnailB).into(imgArtistName)
            artistName.text = listByArtistNameInfo.mp3Artist
            songName.text = listByArtistNameInfo.mp3Title

            cvArtistSongs.setOnClickListener {
                iSelected.onSongClick(listByArtistNameInfo,listByArtistName)
            }

            ivShare.setOnClickListener {

            }
        }
    }
}