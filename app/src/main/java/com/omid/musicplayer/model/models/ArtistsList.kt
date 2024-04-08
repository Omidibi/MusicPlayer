package com.omid.musicplayer.model.models

import com.google.gson.annotations.SerializedName

data class ArtistsList(
    @SerializedName("ONLINE_MP3")
    val onlineMp3: List<ArtistsMp3>
)