package com.omid.musicplayer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoriesMp3(
    @SerializedName("category_image")
    val categoryImage: String,
    @SerializedName("category_image_thumb")
    val categoryImageThumb: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("cid")
    val cid: String
) : Parcelable