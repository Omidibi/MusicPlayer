package com.omid.musicplayer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayListsMp3(
    @SerializedName("pid")
    val pid: String,
    @SerializedName("playlist_image")
    val playlistImage: String,
    @SerializedName("playlist_image_thumb")
    val playlistImageThumb: String,
    @SerializedName("playlist_name")
    val playlistName: String
) : Parcelable