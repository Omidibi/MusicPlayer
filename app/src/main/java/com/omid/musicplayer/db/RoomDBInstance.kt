package com.omid.musicplayer.db

import androidx.room.Room
import com.omid.musicplayer.utils.configuration.AppConfiguration

object RoomDBInstance {

    val roomDbInstance = Room.databaseBuilder(AppConfiguration.getContext(), RoomDataBase::class.java, "tbl_latest")
        .allowMainThreadQueries()
        .build()
}