package com.omid.musicplayer.main.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.MainWidgets
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.AlbumsListMp3
import com.omid.musicplayer.util.configuration.AppConfiguration

class AlbumsListAdapter():RecyclerView.Adapter<AlbumsListVH>() {

    private lateinit var albumsList : List<AlbumsListMp3>
    private lateinit var fragment: Fragment
    private val bundle = Bundle()

    constructor(fragment: Fragment, albumsList : List<AlbumsListMp3>): this(){
        this.albumsList = albumsList
        this.fragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsListVH {
        val view = LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.albums_row,null)
        return AlbumsListVH(view)
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    override fun onBindViewHolder(holder: AlbumsListVH, position: Int) {
        val albumsListInfo = albumsList[position]
        holder.tvNameAlbums.text = albumsListInfo.albumName
        Glide.with(AppConfiguration.getContext()).load(albumsListInfo.albumImageThumb)
            .error(R.drawable.error)
            .placeholder(R.drawable.loading)
            .into(holder.ivAlbums)

        holder.cvAlbums.setOnClickListener {
            bundle.putParcelable("albumsListInfo",albumsListInfo)
            fragment.findNavController().navigate(R.id.action_albumsFragment_to_albumsByIdListFragment,bundle)
            MainWidgets.bnv.visibility = View.GONE
            MainWidgets.toolbar.visibility = View.GONE
        }

        holder.ivShare.setOnClickListener {
            Toast.makeText(AppConfiguration.getContext(),"Share Clicked",Toast.LENGTH_LONG).show()
        }
    }
}