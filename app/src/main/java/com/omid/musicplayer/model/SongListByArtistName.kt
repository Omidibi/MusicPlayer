package com.omid.musicplayer.model

import com.google.gson.annotations.SerializedName

data class SongListByArtistName(
    @SerializedName("ONLINE_MP3")
    val songListByArtistName: List<LatestMp3>
)