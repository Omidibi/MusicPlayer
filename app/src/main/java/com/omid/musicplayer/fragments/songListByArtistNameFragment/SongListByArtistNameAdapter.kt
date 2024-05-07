package com.omid.musicplayer.fragments.songListByArtistNameFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.omid.musicplayer.utils.share.Share

class SongListByArtistNameAdapter(private val listByArtistName: List<LatestMp3>, private val iSelected: IOnSongClickListener, private val fragment: Fragment) : RecyclerView.Adapter<SongListByArtistNameAdapter.SongListByArtistNameVH>() {

    inner class SongListByArtistNameVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvArtistSongs = itemView.findViewById<CardView>(R.id.cv_artist_songs)!!
        val imgArtistName = itemView.findViewById<AppCompatImageView>(R.id.img_artist_name)!!
        val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
        val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
        val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListByArtistNameVH {
        return SongListByArtistNameVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.list_by_artistname_row, null))
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
                iSelected.onSongClick(listByArtistNameInfo, listByArtistName)
            }

            ivShare.setOnClickListener {
                Share.shareMusic(listByArtistNameInfo, fragment)
            }
        }
    }
}