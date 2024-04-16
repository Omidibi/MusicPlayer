package com.omid.musicplayer.model

import com.google.gson.annotations.SerializedName

data class AlbumByIdList(
    @SerializedName("ONLINE_MP3")
    val onlineMp3: List<AlbumByIdMp3>
)