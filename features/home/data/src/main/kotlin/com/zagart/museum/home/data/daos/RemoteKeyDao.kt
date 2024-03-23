package com.zagart.museum.home.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zagart.museum.home.data.models.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(keys: List<RemoteKeyEntity>)

    @Query("SELECT * FROM RemoteKeyEntity WHERE artObjectId = :artObjectId")
    fun getByArtObjectId(artObjectId: String): RemoteKeyEntity

    @Query("DELETE FROM RemoteKeyEntity")
    fun removeAll()

    @Query("SELECT date FROM RemoteKeyEntity ORDER BY date DESC LIMIT 1")
    fun getCacheCreationDate(): Long
}