package com.omid.musicplayer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumByIdMp3(
    @SerializedName("aid")
    val aid: String,
    @SerializedName("album_id")
    val albumId: String,
    @SerializedName("album_image")
    val albumImage: String,
    @SerializedName("album_image_thumb")
    val albumImageThumb: String,
    @SerializedName("album_name")
    val albumName: String,
    @SerializedName("cat_id")
    val catId: String,
    @SerializedName("category_image")
    val categoryImage: String,
    @SerializedName("category_image_thumb")
    val categoryImageThumb: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("cid")
    val cid: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("mp3_artist")
    val mp3Artist: String,
    @SerializedName("mp3_description")
    val mp3Description: String,
    @SerializedName("mp3_duration")
    val mp3Duration: String,
    @SerializedName("mp3_thumbnail_b")
    val mp3ThumbnailB: String,
    @SerializedName("mp3_thumbnail_s")
    val mp3ThumbnailS: String,
    @SerializedName("mp3_title")
    val mp3Title: String,
    @SerializedName("mp3_type")
    val mp3Type: String,
    @SerializedName("mp3_url")
    val mp3Url: String,
    @SerializedName("rate_avg")
    val rateAvg: String,
    @SerializedName("total_download")
    val totalDownload: String,
    @SerializedName("total_rate")
    val totalRate: String,
    @SerializedName("total_views")
    val totalViews: String
) : Parcelable