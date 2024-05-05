package com.omid.musicplayer.fragments.playlistByIdListFragment

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

class PlaylistByIdAdapter(private val playlistByIdMp3: List<LatestMp3>, private val iSelected: IOnSongClickListener) : RecyclerView.Adapter<PlaylistByIdAdapter.PlaylistByIdVH>() {

    inner class PlaylistByIdVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvPlaylistByIdList = itemView.findViewById<CardView>(R.id.cv_playlist_by_id_list)!!
        val imgPlaylist = itemView.findViewById<AppCompatImageView>(R.id.img_playlist)!!
        val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
        val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
        val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistByIdVH {
        return PlaylistByIdVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.playlist_by_id_list_row, null))
    }

    override fun getItemCount(): Int {
        return playlistByIdMp3.size
    }

    override fun onBindViewHolder(holder: PlaylistByIdVH, position: Int) {
        holder.apply {
            val playlistByIdInfo = playlistByIdMp3[position]
            Glide.with(AppConfiguration.getContext()).load(playlistByIdInfo.mp3ThumbnailB).into(imgPlaylist)
            songName.text = playlistByIdInfo.mp3Title
            artistName.text = playlistByIdInfo.mp3Artist
            cvPlaylistByIdList.setOnClickListener {
                iSelected.onSongClick(playlistByIdInfo, playlistByIdMp3)
            }
        }
    }
}