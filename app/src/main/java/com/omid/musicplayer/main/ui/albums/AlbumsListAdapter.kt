package com.omid.musicplayer.main.ui.albums

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.activities.albumsByIdListActivity.AlbumsByIdListActivity
import com.omid.musicplayer.model.models.AlbumsListMp3
import com.omid.musicplayer.util.configuration.AppConfiguration

class AlbumsListAdapter():RecyclerView.Adapter<AlbumsListVH>() {
    private lateinit var albumsList : List<AlbumsListMp3>

    constructor(albumsList : List<AlbumsListMp3>): this(){
    this.albumsList = albumsList

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
           val intent = Intent(AppConfiguration.getContext(), AlbumsByIdListActivity::class.java)

            intent.putExtra("albumsListInfo",albumsListInfo)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            AppConfiguration.getContext().startActivity(intent)
        }

        holder.ivShare.setOnClickListener {
            Toast.makeText(AppConfiguration.getContext(),"Share Clicked",Toast.LENGTH_LONG).show()
        }
    }
}