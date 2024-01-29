package com.omid.musicplayer.main.ui.genres

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.omid.musicplayer.R

class CatListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cvCat = itemView.findViewById<CardView>(R.id.cv_cat)!!
    val tvNameCat = itemView.findViewById<AppCompatTextView>(R.id.tv_name_cat)!!
}