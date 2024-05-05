package com.omid.musicplayer.fragments.specialSongsMoreFragment

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

class SpecialSongsMoreAdapter(private val specialSongs: List<LatestMp3>, private val iSelected: IOnSongClickListener) : RecyclerView.Adapter<SpecialSongsMoreAdapter.SpecialSongsMoreVH>() {

    inner class SpecialSongsMoreVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvSpecialSongs = itemView.findViewById<CardView>(R.id.cv_special_songs)!!
        val imgSpecialSong = itemView.findViewById<AppCompatImageView>(R.id.img_special_song)!!
        val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
        val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
        val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialSongsMoreVH {
        return SpecialSongsMoreVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.special_songs_more_row, null))
    }

    override fun getItemCount(): Int {
        return specialSongs.size
    }

    override fun onBindViewHolder(holder: SpecialSongsMoreVH, position: Int) {
        holder.apply {
            val specialSongsInfo = specialSongs[position]
            artistName.text = specialSongsInfo.mp3Artist
            songName.text = specialSongsInfo.mp3Title
            Glide.with(AppConfiguration.getContext()).load(specialSongsInfo.mp3ThumbnailB).into(imgSpecialSong)
            cvSpecialSongs.setOnClickListener {
                iSelected.onSongClick(specialSongsInfo, specialSongs)
            }
        }
    }
}