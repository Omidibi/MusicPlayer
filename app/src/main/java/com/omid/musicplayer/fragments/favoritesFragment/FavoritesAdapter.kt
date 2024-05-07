package com.omid.musicplayer.fragments.favoritesFragment

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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.db.RoomDBInstance
import com.omid.musicplayer.model.LatestMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration
import com.omid.musicplayer.utils.sendData.IOnSongClickListener
import com.omid.musicplayer.utils.share.Share

class FavoritesAdapter(private val latestMp3: MutableList<LatestMp3>, private val iSelect: IOnSongClickListener, private val fragment: Fragment) : RecyclerView.Adapter<FavoritesAdapter.FavoritesVH>() {

    inner class FavoritesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvFvt = itemView.findViewById<CardView>(R.id.cv_fvt)!!
        val imgFvt = itemView.findViewById<AppCompatImageView>(R.id.img_fvt)!!
        val songName = itemView.findViewById<AppCompatTextView>(R.id.song_name)!!
        val artistName = itemView.findViewById<AppCompatTextView>(R.id.artist_name)!!
        val popupFvt = itemView.findViewById<AppCompatImageView>(R.id.popup_fvt)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        return FavoritesVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.fvt_row, null))
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
                iSelect.onSongClick(latestMp3Info, latestMp3)
            }

            popupFvt.setOnClickListener {
                setupPopupMenu(holder, latestMp3Info, latestMp3, position, fragment)
            }
        }
    }

    private fun setupPopupMenu(holder: FavoritesVH, latestMp3Info: LatestMp3, latestMp3List: MutableList<LatestMp3>, position: Int, fragment: Fragment) {
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
                    Share.shareMusic(latestMp3Info, fragment)
                }

                R.id.delete -> {
                    RoomDBInstance.roomDbInstance.dao().deleteFavorite(latestMp3Info.idPrimaryKey)
                    latestMp3List.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, latestMp3List.size)
                }
            }
            false
        }
    }
}