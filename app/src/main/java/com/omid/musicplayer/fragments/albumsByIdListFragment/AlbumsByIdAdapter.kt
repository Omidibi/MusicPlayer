package com.omid.musicplayer.fragments.albumsByIdListFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.AlbumByIdMp3
import com.omid.musicplayer.util.configuration.AppConfiguration

class AlbumsByIdAdapter() :RecyclerView.Adapter<AlbumsByIdVH>() {
    private lateinit var albumsByIdList : List<AlbumByIdMp3>
    constructor(albumsByIdList : List<AlbumByIdMp3>): this(){
        this.albumsByIdList = albumsByIdList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsByIdVH {
        val view = LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.albums_by_id_list_row,null)
        return AlbumsByIdVH(view)
    }

    override fun getItemCount(): Int {
        return albumsByIdList.size
    }

    override fun onBindViewHolder(holder: AlbumsByIdVH, position: Int) {
        holder.apply {
            val albumsByIdListInfo = albumsByIdList[position]
            singerName.text = albumsByIdListInfo.mp3Artist
            songName.text = albumsByIdListInfo.mp3Title
            Glide.with(AppConfiguration.getContext()).load(albumsByIdListInfo.albumImageThumb)
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .into(imgAlbum)
            clAlbumsByIdList.setOnClickListener {

            }
            ivShare.setOnClickListener {
                Toast.makeText(AppConfiguration.getContext(),"Share Selected",Toast.LENGTH_SHORT).show()
            }
        }
    }
}