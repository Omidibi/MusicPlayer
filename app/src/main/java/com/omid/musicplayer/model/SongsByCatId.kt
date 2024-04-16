package com.omid.musicplayer.model

import com.google.gson.annotations.SerializedName

data class SongsByCatId(
    @SerializedName("ONLINE_MP3")
    val onlineMp3: List<LatestMp3>
)