package com.omid.musicplayer.model.models

import com.google.gson.annotations.SerializedName

data class CategoriesList(
    @SerializedName("ONLINE_MP3")
    val onlineMp3: List<CategoriesMp3>

)