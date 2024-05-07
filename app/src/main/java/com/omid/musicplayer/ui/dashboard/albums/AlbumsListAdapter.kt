package com.omid.musicplayer.ui.dashboard.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.model.AlbumsListMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration

class AlbumsListAdapter() : RecyclerView.Adapter<AlbumsListAdapter.AlbumsListVH>() {

    inner class AlbumsListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvAlbums = itemView.findViewById<CardView>(R.id.cv_albums)!!
        val ivAlbums = itemView.findViewById<AppCompatImageView>(R.id.iv_albums)!!
        val tvNameAlbums = itemView.findViewById<AppCompatTextView>(R.id.tv_name_albums)!!
    }

    private lateinit var albumsList: List<AlbumsListMp3>
    private lateinit var fragment: Fragment
    private val bundle = Bundle()

    constructor(fragment: Fragment, albumsList: List<AlbumsListMp3>) : this() {
        this.albumsList = albumsList
        this.fragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsListVH {
        return AlbumsListVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.albums_row, null))
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    override fun onBindViewHolder(holder: AlbumsListVH, position: Int) {
        holder.apply {
            val albumsListInfo = albumsList[position]
            tvNameAlbums.text = albumsListInfo.albumName
            Glide.with(AppConfiguration.getContext()).load(albumsListInfo.albumImageThumb)
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .into(ivAlbums)

            cvAlbums.setOnClickListener {
                bundle.putParcelable("albumsListInfo", albumsListInfo)
                fragment.findNavController().navigate(R.id.action_albumsFragment_to_albumsByIdListFragment, bundle)
                MainWidgets.bnv.visibility = View.GONE
                MainWidgets.toolbar.visibility = View.GONE
            }
        }
    }
}