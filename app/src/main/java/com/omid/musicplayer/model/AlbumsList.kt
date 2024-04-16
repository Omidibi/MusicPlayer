package com.omid.musicplayer.model

import com.google.gson.annotations.SerializedName

data class AlbumsList(
    @SerializedName("ONLINE_MP3")
    val onlineMp3: List<AlbumsListMp3>
)