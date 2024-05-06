package com.omid.musicplayer.activity

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

class SongSearchAdapter(private val searchList: List<LatestMp3>, private val iSend: IOnSongClickListener) : RecyclerView.Adapter<SongSearchAdapter.SongSearchVH>() {

    inner class SongSearchVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvSongSearch = itemView.findViewById<CardView>(R.id.cv_song_search)!!
        val ivSongSearch = itemView.findViewById<AppCompatImageView>(R.id.iv_song_search)!!
        val tvNameSong = itemView.findViewById<AppCompatTextView>(R.id.tv_name_song)!!
        val tvNameArtist = itemView.findViewById<AppCompatTextView>(R.id.tv_name_artist)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongSearchVH {
        return SongSearchVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.search_list_row, null))
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: SongSearchVH, position: Int) {
        holder.apply {
            val searchListInfo = searchList[position]
            tvNameSong.text = searchListInfo.mp3Title
            tvNameArtist.text = searchListInfo.mp3Artist
            Glide.with(AppConfiguration.getContext()).load(searchListInfo.mp3ThumbnailB)
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .into(ivSongSearch)

            cvSongSearch.setOnClickListener {
                iSend.onSongClick(searchListInfo, searchList)
            }
        }
    }
}