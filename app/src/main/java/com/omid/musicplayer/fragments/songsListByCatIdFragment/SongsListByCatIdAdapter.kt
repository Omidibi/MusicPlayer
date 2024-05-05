package com.omid.musicplayer.fragments.songsListByCatIdFragment

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

class SongsListByCatIdAdapter(private val songsListByCatId: List<LatestMp3>, private val iSelected: IOnSongClickListener) : RecyclerView.Adapter<SongsListByCatIdAdapter.SongsListByCatIdVH>() {

    inner class SongsListByCatIdVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvListByCatId = itemView.findViewById<CardView>(R.id.cv_list_by_cat_id)!!
        val imgSong = itemView.findViewById<AppCompatImageView>(R.id.img_song)!!
        val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
        val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
        val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsListByCatIdVH {
        return SongsListByCatIdVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.songs_by_cat_id_row, null))
    }

    override fun getItemCount(): Int {
        return songsListByCatId.size
    }

    override fun onBindViewHolder(holder: SongsListByCatIdVH, position: Int) {
        holder.apply {
            val listByCatId = songsListByCatId[position]
            songName.text = listByCatId.mp3Title
            artistName.text = listByCatId.mp3Artist
            Glide.with(AppConfiguration.getContext()).load(listByCatId.mp3ThumbnailS)
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .into(imgSong)

            cvListByCatId.setOnClickListener {
                iSelected.onSongClick(listByCatId, songsListByCatId)
            }
        }
    }
}