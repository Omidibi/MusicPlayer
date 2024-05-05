package com.omid.musicplayer.fragments.albumsByIdListFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.AlbumByIdMp3
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class AlbumsByIdAdapter() : RecyclerView.Adapter<AlbumsByIdAdapter.AlbumsByIdVH>() {

    inner class AlbumsByIdVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAlbum = itemView.findViewById<AppCompatImageView>(R.id.img_album)!!
        val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
        val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
        val ivShare = itemView.findViewById<AppCompatImageView>(R.id.iv_share)!!
        val cvAlbumsByIdList = itemView.findViewById<CardView>(R.id.cv_albums_by_id_list)!!
    }

    private lateinit var albumsByIdList: List<AlbumByIdMp3>
    private lateinit var iSelected: IOnSongClickListener

    constructor(albumsByIdList: List<AlbumByIdMp3>, iSelected: IOnSongClickListener) : this() {
        this.albumsByIdList = albumsByIdList
        this.iSelected = iSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsByIdVH {
        return AlbumsByIdVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.albums_by_id_list_row, null))
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
                val latestMp3 = LatestMp3(
                    albumsByIdListInfo.catId,
                    albumsByIdListInfo.categoryImage,
                    albumsByIdListInfo.categoryImageThumb,
                    albumsByIdListInfo.categoryName,
                    albumsByIdListInfo.cid,
                    albumsByIdListInfo.id,
                    albumsByIdListInfo.mp3Artist,
                    albumsByIdListInfo.mp3Description,
                    albumsByIdListInfo.mp3Duration,
                    albumsByIdListInfo.mp3ThumbnailB,
                    albumsByIdListInfo.mp3ThumbnailS,
                    albumsByIdListInfo.mp3Title,
                    albumsByIdListInfo.mp3Type,
                    albumsByIdListInfo.mp3Url,
                    albumsByIdListInfo.rateAvg,
                    albumsByIdListInfo.totalDownload,
                    albumsByIdListInfo.totalRate,
                    albumsByIdListInfo.totalViews
                )
                iSelected.onSongClick(latestMp3, albumsByIdList.map { it.toLatestMp3() })
            }

            ivShare.setOnClickListener {
                Toast.makeText(AppConfiguration.getContext(), "Share Selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun AlbumByIdMp3.toLatestMp3(): LatestMp3 {
        return LatestMp3(
            catId,
            categoryImage,
            categoryImageThumb,
            categoryName,
            cid,
            id,
            mp3Artist,
            mp3Description,
            mp3Duration,
            mp3ThumbnailB,
            mp3ThumbnailS,
            mp3Title,
            mp3Type,
            mp3Url,
            rateAvg,
            totalDownload,
            totalRate,
            totalViews
        )
    }
}