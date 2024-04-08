package com.omid.musicplayer.model.models

import com.google.gson.annotations.SerializedName

data class SongListByArtistName(
    @SerializedName("ONLINE_MP3")
    val songListByArtistName: List<LatestMp3>
)