package com.omid.musicplayer.model

import com.google.gson.annotations.SerializedName

data class PlaylistByIdList(
    @SerializedName("ONLINE_MP3")
    val onlineMp3: List<PlaylistByIdMp3>
)