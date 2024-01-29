package com.omid.musicplayer.main.ui.home

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.RecentArtistListMp3
import com.omid.musicplayer.util.configuration.AppConfiguration

class RecentArtistAdapter(private val recentArtist : List<RecentArtistListMp3>): RecyclerView.Adapter<RecentArtistVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentArtistVH {
        val view = LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.recent_artist_row,null)
        return RecentArtistVH(view)
    }

    override fun getItemCount(): Int {
        return recentArtist.size
    }

    override fun onBindViewHolder(holder: RecentArtistVH, position: Int) {
        holder.apply {
            val recentArtistInfo = recentArtist[position]
            artistNameRecent.text = recentArtistInfo.artistName
            Glide.with(AppConfiguration.getContext()).load(recentArtistInfo.artistImageThumb)
                .error(R.drawable.error)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        artistNameRecent.visibility = View.GONE
                        val padding = AppConfiguration.getContext().resources.getDimensionPixelSize(R.dimen._12dp)
                        ivRecentArtist.setPadding(padding)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                })
                .placeholder(R.drawable.loading)
                .into(ivRecentArtist)
            cvRecentArtist.setOnClickListener {
                Toast.makeText(AppConfiguration.getContext(),"${recentArtistInfo.artistName} Clicked",Toast.LENGTH_SHORT).show()
            }
        }
    }
}