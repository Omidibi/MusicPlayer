package com.omid.musicplayer.utils.practicalCodes

import android.view.View
import com.omid.musicplayer.activity.MainWidgets

class MainWidgetStatus {

    companion object {

        fun visible(){
            MainWidgets.bnv.visibility = View.VISIBLE
            MainWidgets.toolbar.visibility = View.VISIBLE
        }

        fun gone(){
            MainWidgets.bnv.visibility = View.GONE
            MainWidgets.toolbar.visibility = View.GONE
        }
    }
}