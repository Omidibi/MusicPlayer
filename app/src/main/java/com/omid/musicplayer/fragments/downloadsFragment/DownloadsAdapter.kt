package com.omid.musicplayer.fragments.downloadsFragment

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.db.RoomDBInstance
import com.omid.musicplayer.model.DownloadedMp3
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class DownloadsAdapter(private val downloadedMp3: MutableList<DownloadedMp3>, private val iSelected: IOnSongClickListener) : RecyclerView.Adapter<DownloadsAdapter.DownloadsVH>() {

    inner class DownloadsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvDownload = itemView.findViewById<CardView>(R.id.cv_download)!!
        val imgDownload = itemView.findViewById<AppCompatImageView>(R.id.img_download)!!
        val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
        val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
        val popupDownload = itemView.findViewById<AppCompatImageView>(R.id.popup_download)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadsVH {
        return DownloadsVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.download_row, null))
    }

    override fun getItemCount(): Int {
        return downloadedMp3.size
    }

    override fun onBindViewHolder(holder: DownloadsVH, position: Int) {
        holder.apply {
            val latestMp3Info = downloadedMp3[position]
            songName.text = latestMp3Info.mp3Title
            artistName.text = latestMp3Info.mp3Artist
            Glide.with(AppConfiguration.getContext()).load(latestMp3Info.mp3ThumbnailB).into(imgDownload)
            cvDownload.setOnClickListener {
                val latestMp3 = LatestMp3(
                    latestMp3Info.catId,
                    latestMp3Info.categoryImage,
                    latestMp3Info.categoryImageThumb,
                    latestMp3Info.categoryName,
                    latestMp3Info.cid,
                    latestMp3Info.id,
                    latestMp3Info.mp3Artist,
                    latestMp3Info.mp3Description,
                    latestMp3Info.mp3Duration,
                    latestMp3Info.mp3ThumbnailB,
                    latestMp3Info.mp3ThumbnailS,
                    latestMp3Info.mp3Title,
                    latestMp3Info.mp3Type,
                    latestMp3Info.mp3Url,
                    latestMp3Info.rateAvg,
                    latestMp3Info.totalDownload,
                    latestMp3Info.totalRate,
                    latestMp3Info.totalViews
                )
                iSelected.onSongClick(latestMp3, downloadedMp3.map { it.toLatestMp3() })
            }

            popupDownload.setOnClickListener {
                setupPopupMenu(holder, latestMp3Info, downloadedMp3, position)
            }
        }
    }

    private fun setupPopupMenu(holder: DownloadsVH, downloadedMp3: DownloadedMp3, downloadedMp3List: MutableList<DownloadedMp3>, position: Int) {
        val popup = PopupMenu(AppConfiguration.getContext(), holder.popupDownload)
        popup.inflate(R.menu.download_popup_menu)
        popup.show()
        popup.menu.forEach { item ->
            SpannableString(item.title.toString()).apply {
                this.setSpan(ForegroundColorSpan(Color.BLACK), 0, this.length, 0)
                item.title = this
            }
        }
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.d_share_fvt -> {

                }

                R.id.d_delete -> {
                    RoomDBInstance.roomDbInstance.dao().deleteDownload(downloadedMp3.idPrimaryKey)
                    downloadedMp3List.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, downloadedMp3List.size)
                }
            }
            false
        }
    }

    private fun DownloadedMp3.toLatestMp3(): LatestMp3 {
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