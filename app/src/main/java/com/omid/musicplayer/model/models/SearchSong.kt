package com.omid.musicplayer.model.models

import com.google.gson.annotations.SerializedName

data class SearchSong(
    @SerializedName("ONLINE_MP3")
    val onlineMp3: List<SearchSongMp3>
)