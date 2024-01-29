package com.omid.musicplayer.main.ui.genres

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R
import com.omid.musicplayer.activities.songsListByCatIdActivity.SongsListByCatIdActivity
import com.omid.musicplayer.model.models.CategoriesMp3
import com.omid.musicplayer.util.configuration.AppConfiguration

class CatListAdapter (private val catList : List<CategoriesMp3>): RecyclerView.Adapter<CatListVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatListVH {
        val view = LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.cat_row,null)
        return CatListVH(view)
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: CatListVH, position: Int) {
        val catListInfo = catList[position]
        holder.tvNameCat.text = catListInfo.categoryName
        holder.cvCat.setOnClickListener {
            val intent = Intent(AppConfiguration.getContext(), SongsListByCatIdActivity::class.java)
            intent.putExtra("catListInfo",catListInfo)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            AppConfiguration.getContext().startActivity(intent)
        }
    }
}