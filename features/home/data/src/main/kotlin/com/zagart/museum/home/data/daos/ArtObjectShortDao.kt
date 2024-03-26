package com.zagart.museum.home.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zagart.museum.home.data.models.ArtObjectShortEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtObjectShortDao {

    @Query("DELETE FROM ArtObjectShortEntity")
    fun removeAll()

    @Query("SELECT * FROM ArtObjectShortEntity ORDER BY date")
    fun getAll(): Flow<List<ArtObjectShortEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<ArtObjectShortEntity>)

    @Query("SELECT date FROM ArtObjectShortEntity ORDER BY date DESC LIMIT 1")
    fun getCacheCreationDate(): Long

    @Query("SELECT Count(id) FROM ArtObjectShortEntity")
    fun getCount(): Int
}