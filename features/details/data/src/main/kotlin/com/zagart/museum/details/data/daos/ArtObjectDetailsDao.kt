package com.zagart.museum.details.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.zagart.museum.details.data.models.ArtObjectDetailsEntity

@Dao
interface ArtObjectDetailsDao {

    @Query("SELECT * FROM ArtObjectDetailsEntity WHERE objectNumber = :objectNumber")
    fun getByObjectNumber(objectNumber : String): ArtObjectDetailsEntity?

    @Upsert
    fun upsert(details: ArtObjectDetailsEntity)
}