package com.omid.musicplayer.util.sendData

import com.omid.musicplayer.model.models.LatestMp3

interface IOnSongClickListener {

    fun onSongClick(latestSongInfo : LatestMp3, latestSongsList: List<LatestMp3>)
}