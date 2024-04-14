package com.omid.musicplayer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.omid.musicplayer.model.models.LatestMp3

@Dao
interface IDao {

    @Insert
    fun insert(vararg latestMp3: LatestMp3)

    @Query("Select * From tbl_latest")
    fun showAllLatest(): MutableList<LatestMp3>

    @Query("Delete From tbl_latest Where idPrimaryKey Like :idPrimaryKey")
    fun delete(idPrimaryKey: Int): Int

    @Query("Select * From tbl_latest Where idPrimaryKey Like :idPrimaryKey")
    fun searchByIdPrimary(idPrimaryKey: Int): MutableList<LatestMp3>

    @Query("Select * From tbl_latest Where id Like :id")
    fun searchById(id: String): MutableList<LatestMp3>
}