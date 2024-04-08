package com.omid.musicplayer.utils.sendData

import com.omid.musicplayer.model.models.LatestMp3

interface IOnSongClickListener {

    fun onSongClick(latestSongInfo: LatestMp3, latestSongsList: List<LatestMp3>)
}