package com.omid.musicplayer

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sothree.slidinguppanel.SlidingUpPanelLayout

object MainWidgets {

    lateinit var bnv: BottomNavigationView
    lateinit var slidingUpPanel: SlidingUpPanelLayout
    @SuppressLint("StaticFieldLeak")
    lateinit var toolbar: Toolbar
}