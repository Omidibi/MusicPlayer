package com.omid.musicplayer.fragments.newSongsMoreFragment

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

class NewSongsMoreAdapter(private val newSongs: List<LatestMp3>, private val iSelected: IOnSongClickListener, private val fragment: Fragment) : RecyclerView.Adapter<NewSongsMoreAdapter.NewSongsMoreVH>() {

    inner class NewSongsMoreVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvNewSongs = itemView.findViewById<CardView>(R.id.cv_new_songs)!!
        val imgNewSong = itemView.findViewById<AppCompatImageView>(R.id.img_new_song)!!
        val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
        val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
        val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewSongsMoreVH {
        return NewSongsMoreVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.new_songs_more_row, null))
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
                iSelected.onSongClick(newSongsInfo, newSongs)
            }
            ivShare.setOnClickListener {
                Share.shareMusic(newSongsInfo, fragment)
            }
        }
    }
}