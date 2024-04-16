package com.omid.musicplayer.utils.sendData

import com.omid.musicplayer.model.LatestMp3

interface IOnSongClickListener {

    fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>)
}