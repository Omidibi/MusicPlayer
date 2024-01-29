package com.omid.musicplayer.main.ui.artists

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.ArtistsMp3
import com.omid.musicplayer.util.configuration.AppConfiguration

class ArtistAdapter(private val artistsList : List<ArtistsMp3>): RecyclerView.Adapter<ArtistVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistVH {
        val view = LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.artists_row,null)
        return ArtistVH(view)
    }

    override fun getItemCount(): Int {
        return artistsList.size
    }

    override fun onBindViewHolder(holder: ArtistVH, position: Int) {
       val artistsListInfo = artistsList[position]
        holder.tvNameArtist.text = artistsListInfo.artistName
        Glide.with(AppConfiguration.getContext()).load(artistsListInfo.artistImageThumb)
            .error(R.drawable.error)
            .placeholder(R.drawable.loading)
            .into(holder.ivArtist)
        holder.cvArtist.setOnClickListener {
            Toast.makeText(AppConfiguration.getContext(),"${artistsListInfo.artistName} Clicked",Toast.LENGTH_LONG).show()
        }
        holder.ivShare.setOnClickListener {
            Toast.makeText(AppConfiguration.getContext(),"Share is Clicked",Toast.LENGTH_LONG).show()
        }
        holder.aboutSinger.setOnClickListener {
            Toast.makeText(AppConfiguration.getContext(),"about singer is Clicked",Toast.LENGTH_LONG).show()
        }
    }
}