package com.omid.musicplayer.fragments.albumsByIdListFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.AlbumByIdMp3
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class AlbumsByIdAdapter() :RecyclerView.Adapter<AlbumsByIdVH>() {

    private lateinit var albumsByIdList : List<AlbumByIdMp3>
    private lateinit var iSelected: IOnSongClickListener

    constructor(albumsByIdList : List<AlbumByIdMp3>, iSelected: IOnSongClickListener): this(){
        this.albumsByIdList = albumsByIdList
        this.iSelected = iSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsByIdVH {
        return AlbumsByIdVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.albums_by_id_list_row,null))
    }

    override fun getItemCount(): Int {
        return albumsByIdList.size
    }

    override fun onBindViewHolder(holder: AlbumsByIdVH, position: Int) {
        holder.apply {
            val albumsByIdListInfo = albumsByIdList[position]
            artistName.text = albumsByIdListInfo.mp3Artist
            songName.text = albumsByIdListInfo.mp3Title
            Glide.with(AppConfiguration.getContext()).load(albumsByIdListInfo.albumImageThumb)
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .into(imgAlbum)

            cvAlbumsByIdList.setOnClickListener {
                val latestMp3 = LatestMp3(albumsByIdListInfo.catId,albumsByIdListInfo.categoryImage,albumsByIdListInfo.categoryImageThumb,albumsByIdListInfo.categoryName,albumsByIdListInfo.cid,albumsByIdListInfo.id,albumsByIdListInfo.mp3Artist,albumsByIdListInfo.mp3Description,albumsByIdListInfo.mp3Duration,albumsByIdListInfo.mp3ThumbnailB,albumsByIdListInfo.mp3ThumbnailS,albumsByIdListInfo.mp3Title,albumsByIdListInfo.mp3Type,albumsByIdListInfo.mp3Url,albumsByIdListInfo.rateAvg,albumsByIdListInfo.totalDownload,albumsByIdListInfo.totalRate,albumsByIdListInfo.totalViews)
                iSelected.onSongClick(latestMp3, albumsByIdList.map { it.toLatestMp3() })
            }

            ivShare.setOnClickListener {
                Toast.makeText(AppConfiguration.getContext(),"Share Selected",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun AlbumByIdMp3.toLatestMp3(): LatestMp3 {
        return LatestMp3(catId, categoryImage, categoryImageThumb, categoryName, cid, id, mp3Artist, mp3Description, mp3Duration, mp3ThumbnailB, mp3ThumbnailS, mp3Title, mp3Type, mp3Url, rateAvg, totalDownload, totalRate, totalViews)
    }
}