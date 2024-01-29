package com.omid.musicplayer.activities.searchActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.SearchSongMp3
import com.omid.musicplayer.util.configuration.AppConfiguration

class SongSearchAdapter(private val searchList: List<SearchSongMp3>): RecyclerView.Adapter<SongSearchVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongSearchVH {
        val view = LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.search_list_row,null)
        return SongSearchVH(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: SongSearchVH, position: Int) {
        holder.apply {
            val searchListInfo = searchList[position]
            tvNameSong.text = searchListInfo.mp3Title
            tvNameArtist.text = searchListInfo.mp3Artist
            Glide.with(AppConfiguration.getContext()).load(searchListInfo.mp3ThumbnailB)
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .into(ivSongSearch)

            cvSongSearch.setOnClickListener {
                Toast.makeText(AppConfiguration.getContext(),"is ok",Toast.LENGTH_SHORT).show()
            }
        }
    }
}