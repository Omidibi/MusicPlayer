package com.omid.musicplayer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.omid.musicplayer.model.DownloadedMp3
import com.omid.musicplayer.model.LatestMp3

@Dao
interface IDao {

    @Insert
    fun insert(vararg latestMp3: LatestMp3)

    @Insert
    fun insertDownload(vararg downloadedMp3: DownloadedMp3)

    @Query("Select * From tbl_latest")
    fun showAll(): MutableList<LatestMp3>

    @Query("Select * From tbl_downloads")
    fun showAllDownload(): MutableList<DownloadedMp3>

    @Query("Delete From tbl_latest Where idPrimaryKey Like :idPrimaryKey")
    fun delete(idPrimaryKey: Int): Int

    @Query("Delete From tbl_downloads Where idPrimaryKey Like :idPrimaryKey")
    fun deleteDownload(idPrimaryKey: Int): Int

    @Query("Select * From tbl_latest Where id Like :id")
    fun searchById(id: String): MutableList<LatestMp3>

    @Query("Select * From tbl_downloads Where id Like :id")
    fun searchByIdDownload(id: String): MutableList<DownloadedMp3>
}