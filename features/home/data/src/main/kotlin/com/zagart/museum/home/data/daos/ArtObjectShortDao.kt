package com.zagart.museum.home.data.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zagart.museum.home.data.models.ArtObjectShortEntity

@Dao
interface ArtObjectShortDao {

    @Query("DELETE FROM ArtObjectShortEntity")
    fun removeAll()

    @Query("SELECT * FROM ArtObjectShortEntity ORDER BY page")
    fun getAll(): PagingSource<Int, ArtObjectShortEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<ArtObjectShortEntity>)
}