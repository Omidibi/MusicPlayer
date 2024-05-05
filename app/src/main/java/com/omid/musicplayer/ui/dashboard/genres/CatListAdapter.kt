package com.omid.musicplayer.ui.dashboard.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R
import com.omid.musicplayer.activity.MainWidgets
import com.omid.musicplayer.model.CategoriesMp3
import com.omid.musicplayer.utils.configuration.AppConfiguration

class CatListAdapter(private val fragment: Fragment, private val catList: List<CategoriesMp3>) : RecyclerView.Adapter<CatListAdapter.CatListVH>() {

    inner class CatListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvCat = itemView.findViewById<CardView>(R.id.cv_cat)!!
        val tvNameCat = itemView.findViewById<AppCompatTextView>(R.id.tv_name_cat)!!
    }

    private val bundle = Bundle()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatListVH {
        return CatListVH(LayoutInflater.from(AppConfiguration.getContext()).inflate(R.layout.cat_row, null))
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: CatListVH, position: Int) {
        holder.apply {
            val catListInfo = catList[position]
            tvNameCat.text = catListInfo.categoryName
            cvCat.setOnClickListener {
                bundle.putParcelable("catListInfo", catListInfo)
                fragment.findNavController().navigate(R.id.action_genresFragment_to_songsListByCatIdFragment, bundle)
                MainWidgets.bnv.visibility = View.GONE
                MainWidgets.toolbar.visibility = View.GONE
            }
        }
    }
}