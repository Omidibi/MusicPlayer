package com.omid.musicplayer.model.models

import com.google.gson.annotations.SerializedName

data class RecentArtistListMp3(
    @SerializedName("artist_image")
    val artistImage: String,
    @SerializedName("artist_image_thumb")
    val artistImageThumb: String,
    @SerializedName("artist_name")
    val artistName: String,
    @SerializedName("id")
    val id: String

)