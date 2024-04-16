package com.omid.musicplayer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumsListMp3(
    @SerializedName("aid")
    val aid: String,
    @SerializedName("album_image")
    val albumImage: String,
    @SerializedName("album_image_thumb")
    val albumImageThumb: String,
    @SerializedName("album_name")
    val albumName: String
) : Parcelable