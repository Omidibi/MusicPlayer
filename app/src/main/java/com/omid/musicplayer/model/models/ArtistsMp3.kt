package com.omid.musicplayer.model.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistsMp3(
    @SerializedName("artist_image")
    val artistImage: String,
    @SerializedName("artist_image_thumb")
    val artistImageThumb: String,
    @SerializedName("artist_name")
    val artistName: String,
    @SerializedName("id")
    val id: String
) : Parcelable