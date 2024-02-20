package com.omid.musicplayer.main.ui.playLists

import android.content.Intent
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
import com.omid.musicplayer.activities.playlistByIdListActivity.PlaylistByIdListActivity
import com.omid.musicplayer.model.models.PlayListsMp3
import com.omid.musicplayer.util.configuration.AppConfiguration

class PlayListsAdapter(private val plyLists : List<PlayListsMp3>): RecyclerView.Adapter<PlayListsVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListsVH {
        val view = LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.playlists_row,null)
            return PlayListsVH(view)
    }

    override fun getItemCount(): Int {
        return plyLists.size
    }

    override fun onBindViewHolder(holder: PlayListsVH, position: Int) {
       val playListsInfo = plyLists[position]
        holder.tvNamePlaylists.text = playListsInfo.playlistName
        Glide.with(AppConfiguration.getContext()).load(playListsInfo.playlistImageThumb)
            .error(R.drawable.error)
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.tvNamePlaylists.visibility = View.GONE
                    val padding = AppConfiguration.getContext().resources.getDimensionPixelSize(R.dimen._12dp)
                    holder.ivPlaylists.setPadding(padding)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                   return false
                }

            })
            .placeholder(R.drawable.loading)
            .into(holder.ivPlaylists)

        holder.cvPlaylists.setOnClickListener {
            val intent = Intent(AppConfiguration.getContext(), PlaylistByIdListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("playListsInfo",playListsInfo)
            AppConfiguration.getContext().startActivity(intent)
        }
    }
}