package com.omid.musicplayer.model.models

import com.google.gson.annotations.SerializedName

data class LatestSong(
    @SerializedName("ONLINE_MP3")
    val onlineMp3: List<LatestMp3>
)