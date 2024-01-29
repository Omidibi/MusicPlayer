package com.omid.musicplayer.model.models

import com.google.gson.annotations.SerializedName

data class PlayLists(
    @SerializedName("ONLINE_MP3")
    val onlineMp3: List<PlayListsMp3>

)