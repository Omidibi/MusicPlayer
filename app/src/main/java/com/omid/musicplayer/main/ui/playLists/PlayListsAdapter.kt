package com.omid.musicplayer.main.ui.playLists

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.omid.musicplayer.MainWidgets
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.PlayListsMp3
import com.omid.musicplayer.util.configuration.AppConfiguration

class PlayListsAdapter(private val fragment: Fragment,private val plyLists : List<PlayListsMp3>): RecyclerView.Adapter<PlayListsVH>() {

    val bundle = Bundle()

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
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    holder.tvNamePlaylists.visibility = View.GONE
                    val padding = AppConfiguration.getContext().resources.getDimensionPixelSize(R.dimen._12dp)
                    holder.ivPlaylists.setPadding(padding)
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                   return false
                }

            })
            .placeholder(R.drawable.loading)
            .into(holder.ivPlaylists)

        holder.cvPlaylists.setOnClickListener {
            bundle.putParcelable("playListsInfo",playListsInfo)
            fragment.findNavController().navigate(R.id.action_playListsFragment_to_playlistByIdListFragment,bundle)
            MainWidgets.bnv.visibility = View.GONE
            MainWidgets.toolbar.visibility = View.GONE
        }
    }
}