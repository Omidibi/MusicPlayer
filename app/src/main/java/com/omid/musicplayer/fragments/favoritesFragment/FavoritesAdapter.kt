package com.omid.musicplayer.fragments.favoritesFragment

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.db.RoomDBInstance
import com.omid.musicplayer.model.models.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener

class FavoritesAdapter(private val latestMp3: MutableList<LatestMp3>,private val iSelect: IOnSongClickListener): RecyclerView.Adapter<FavoritesVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        return FavoritesVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.fvt_row,null))
    }

    override fun getItemCount(): Int {
        return latestMp3.size
    }

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
        holder.apply {
            val latestMp3Info = latestMp3[position]

            artistName.text = latestMp3Info.mp3Artist
            songName.text = latestMp3Info.mp3Title
            Glide.with(AppConfiguration.getContext()).load(latestMp3Info.mp3ThumbnailB).into(imgFvt)

            cvFvt.setOnClickListener {
                iSelect.onSongClick(latestMp3Info,latestMp3)
            }

            popupFvt.setOnClickListener {
                setupPopupMenu(holder,latestMp3Info,latestMp3,position)
            }
        }
    }

    private fun setupPopupMenu(holder: FavoritesVH,latestMp3Info : LatestMp3,latestMp3List : MutableList<LatestMp3>,position: Int) {
            val popup = PopupMenu(AppConfiguration.getContext(), holder.popupFvt)
            popup.inflate(R.menu.fvt_popup_menu)
            popup.show()
            popup.menu.forEach { item ->
                SpannableString(item.title.toString()).apply {
                    this.setSpan(ForegroundColorSpan(Color.BLACK), 0, this.length, 0)
                    item.title = this
                }
            }
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.share_fvt -> {

                    }

                    R.id.delete -> {
                        RoomDBInstance.roomDbInstance.dao().delete(latestMp3Info.idPrimaryKey)
                        latestMp3List.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position,latestMp3List.size)
                    }
                }
                false
            }
    }
}