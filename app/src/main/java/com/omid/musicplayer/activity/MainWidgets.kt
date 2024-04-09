package com.omid.musicplayer.activity

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import androidx.media3.exoplayer.ExoPlayer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sothree.slidinguppanel.SlidingUpPanelLayout

object MainWidgets {

    lateinit var bnv: BottomNavigationView
    lateinit var slidingUpPanel: SlidingUpPanelLayout
    @SuppressLint("StaticFieldLeak")
    lateinit var toolbar: Toolbar
    lateinit var player: ExoPlayer
}