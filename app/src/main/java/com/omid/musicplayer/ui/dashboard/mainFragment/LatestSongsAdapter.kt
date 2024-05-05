package com.omid.musicplayer.ui.dashboard.mainFragment

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.omid.musicplayer.R
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class LatestSongsAdapter() : RecyclerView.Adapter<LatestSongsAdapter.LatestSongsVH>() {

    inner class LatestSongsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
        val musicName = itemView.findViewById<AppCompatTextView>(R.id.music_name)!!
        val ivLatestSongs = itemView.findViewById<AppCompatImageView>(R.id.iv_latest_songs)!!
        val cvLatestSongs = itemView.findViewById<CardView>(R.id.cv_latest_songs)!!
    }

    lateinit var activity: Activity
    private lateinit var songsList: List<LatestMp3>
    private lateinit var iSelected: IOnSongClickListener

    constructor(activity: Activity, latestSongsList: List<LatestMp3>, iSelected: IOnSongClickListener) : this() {
        this.activity = activity
        songsList = latestSongsList
        this.iSelected = iSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestSongsVH {
        return LatestSongsVH(LayoutInflater.from(activity).inflate(R.layout.latest_songs_row, null))
    }

    override fun getItemCount(): Int {
        return songsList.size
    }

    override fun onBindViewHolder(holder: LatestSongsVH, position: Int) {
        holder.apply {
            val latestSongInfo = songsList[position]
            artistName.text = latestSongInfo.mp3Artist
            musicName.text = latestSongInfo.mp3Title
            Glide.with(AppConfiguration.getContext()).load(latestSongInfo.mp3ThumbnailB)
                .error(R.drawable.error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        artistName.visibility = View.GONE
                        musicName.visibility = View.GONE
                        val padding = activity.resources.getDimensionPixelSize(R.dimen._12dp)
                        ivLatestSongs.setPadding(padding)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        return false
                    }

                })
                .placeholder(R.drawable.loading)
                .into(ivLatestSongs)

            cvLatestSongs.setOnClickListener {
                iSelected.onSongClick(latestSongInfo, songsList)
            }
        }
    }
}