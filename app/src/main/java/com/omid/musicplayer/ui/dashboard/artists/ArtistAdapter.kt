package com.omid.musicplayer.ui.dashboard.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.ArtistsMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration

class ArtistAdapter(private val fragment: Fragment,private val artistsList : List<ArtistsMp3>): RecyclerView.Adapter<ArtistVH>() {

    private val bundle = Bundle()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistVH {
        return ArtistVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.artists_row,null))
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
            bundle.putParcelable("ArtistInfo",artistsListInfo)
            fragment.findNavController().navigate(R.id.action_artistsFragment_to_songListByArtistNameFragment,bundle)
            MainWidgets.bnv.visibility = View.GONE
            MainWidgets.toolbar.visibility = View.GONE
        }

        holder.ivShare.setOnClickListener {
            Toast.makeText(AppConfiguration.getContext(),"Share is Clicked",Toast.LENGTH_LONG).show()
        }

        holder.aboutSinger.setOnClickListener {
            Toast.makeText(AppConfiguration.getContext(),"about singer is Clicked",Toast.LENGTH_LONG).show()
        }
    }
}