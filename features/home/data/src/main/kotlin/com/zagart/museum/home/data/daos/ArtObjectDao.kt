package com.zagart.museum.home.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zagart.museum.home.data.models.ArtObjectEntity

@Dao
interface ArtObjectDao {

    @Query("SELECT * FROM ArtObjectEntity")
    fun getAll(): List<ArtObjectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<ArtObjectEntity>)
}