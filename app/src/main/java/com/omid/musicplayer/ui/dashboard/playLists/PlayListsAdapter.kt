package com.omid.musicplayer.ui.dashboard.playLists

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.model.PlayListsMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration

class PlayListsAdapter(private val fragment: Fragment, private val plyLists: List<PlayListsMp3>) : RecyclerView.Adapter<PlayListsAdapter.PlayListsVH>() {

    inner class PlayListsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvPlaylists = itemView.findViewById<CardView>(R.id.cv_playlists)!!
        val ivPlaylists = itemView.findViewById<AppCompatImageView>(R.id.iv_playlists)!!
        val tvNamePlaylists = itemView.findViewById<AppCompatTextView>(R.id.tv_name_playlists)!!
    }

    val bundle = Bundle()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListsVH {
        return PlayListsVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.playlists_row, null))
    }

    override fun getItemCount(): Int {
        return plyLists.size
    }

    override fun onBindViewHolder(holder: PlayListsVH, position: Int) {
        holder.apply {
            val playListsInfo = plyLists[position]
            tvNamePlaylists.text = playListsInfo.playlistName
            Glide.with(AppConfiguration.getContext()).load(playListsInfo.playlistImageThumb)
                .error(R.drawable.error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        tvNamePlaylists.visibility = View.GONE
                        val padding = AppConfiguration.getContext().resources.getDimensionPixelSize(R.dimen._12dp)
                        ivPlaylists.setPadding(padding)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        return false
                    }

                })
                .placeholder(R.drawable.loading)
                .into(ivPlaylists)

            cvPlaylists.setOnClickListener {
                bundle.putParcelable("playListsInfo", playListsInfo)
                fragment.findNavController().navigate(R.id.action_playListsFragment_to_playlistByIdListFragment, bundle)
                MainWidgets.bnv.visibility = View.GONE
                MainWidgets.toolbar.visibility = View.GONE
            }
        }
    }
}