package com.omid.musicplayer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omid.musicplayer.model.DownloadedMp3
import com.omid.musicplayer.model.LatestMp3

@Database(entities = [LatestMp3::class, DownloadedMp3::class], version = 1)
abstract class RoomDataBase : RoomDatabase() {

    abstract fun dao(): IDao
}