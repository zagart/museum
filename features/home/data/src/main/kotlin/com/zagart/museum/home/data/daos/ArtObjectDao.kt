package com.zagart.museum.home.data.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zagart.museum.home.data.models.ArtObjectEntity

@Dao
interface ArtObjectDao {

    @Query("DELETE FROM ArtObjectEntity")
    fun removeAll()

    @Query("SELECT * FROM ArtObjectEntity ORDER BY page")
    fun getAll(): PagingSource<Int, ArtObjectEntity>

    @Query("SELECT * FROM ArtObjectEntity WHERE page = :page")
    fun getByPage(page: Int): List<ArtObjectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<ArtObjectEntity>)
}