package com.omid.musicplayer.ui.dashboard.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.R
import com.omid.musicplayer.model.models.CategoriesMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration

class CatListAdapter (private val fragment: Fragment,private val catList : List<CategoriesMp3>): RecyclerView.Adapter<CatListVH>() {

    private val bundle = Bundle()

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
            bundle.putParcelable("catListInfo",catListInfo)
            fragment.findNavController().navigate(R.id.action_genresFragment_to_songsListByCatIdFragment,bundle)
            MainWidgets.bnv.visibility = View.GONE
            MainWidgets.toolbar.visibility = View.GONE
        }
    }
}