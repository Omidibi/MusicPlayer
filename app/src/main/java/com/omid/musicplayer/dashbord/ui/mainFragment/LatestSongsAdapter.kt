package com.omid.musicplayer.dashbord.ui.mainFragment

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.util.configuration.AppConfiguration
import com.omid.musicplayer.util.sendData.IOnSongClickListener

class LatestSongsAdapter() : RecyclerView.Adapter<LatestSongsVH>() {

    lateinit var activity: Activity
    private lateinit var songsList :  List<LatestMp3>
    private lateinit var iSelected : IOnSongClickListener

    constructor(activity: Activity, latestSongsList: List<LatestMp3>, iSelected : IOnSongClickListener) : this() {
        this.activity = activity
        songsList = latestSongsList
        this.iSelected = iSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestSongsVH {
        val view = LayoutInflater.from(activity).inflate(R.layout.latest_songs_row, null)
        return LatestSongsVH(view)
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
                iSelected.onSongClick(latestSongInfo,songsList)
            }
        }
    }
}