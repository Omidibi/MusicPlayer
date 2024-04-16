package com.omid.musicplayer.ui.dashboard.mainFragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.R
import com.omid.musicplayer.model.ArtistsMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration

class RecentArtistAdapter(private val fragment: MainFragment, private val recentArtist : List<ArtistsMp3>): RecyclerView.Adapter<RecentArtistVH>() {

    private val bundle = Bundle()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentArtistVH {
        return RecentArtistVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.recent_artist_row,null))
    }

    override fun getItemCount(): Int {
        return recentArtist.size
    }

    override fun onBindViewHolder(holder: RecentArtistVH, position: Int) {
        holder.apply {
            val recentArtistInfo = recentArtist[position]
            artistNameRecent.text = recentArtistInfo.artistName
            Glide.with(AppConfiguration.getContext())
                .load(recentArtistInfo.artistImageThumb)
                .circleCrop()
                .error(R.drawable.error)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        artistNameRecent.visibility = View.GONE
                        val padding = AppConfiguration.getContext().resources.getDimensionPixelSize(R.dimen._12dp)
                        ivRecentArtist.setPadding(padding)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        return false
                    }

                })
                .placeholder(R.drawable.loading)
                .into(ivRecentArtist)

            clRecentArtist.setOnClickListener {
                bundle.putParcelable("ArtistInfo",recentArtistInfo)
                fragment.findNavController().navigate(R.id.action_mainFragment_to_songListByArtistNameFragment,bundle)
                MainWidgets.bnv.visibility = View.GONE
                MainWidgets.toolbar.visibility = View.GONE
            }
        }
    }
}