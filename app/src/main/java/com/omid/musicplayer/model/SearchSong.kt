package com.omid.musicplayer.model

import com.google.gson.annotations.SerializedName

data class SearchSong(
    @SerializedName("ONLINE_MP3")
    val onlineMp3: List<LatestMp3>
)