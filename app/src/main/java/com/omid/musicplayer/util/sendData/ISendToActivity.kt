package com.omid.musicplayer.util.sendData

import com.omid.musicplayer.model.models.LatestMp3

interface ISendToActivity {

    fun sendSongInfo(latestMp3: LatestMp3, latestSongsList: List<LatestMp3>)
}