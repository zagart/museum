package com.zagart.museum.home.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArtObjectShortEntity(
    @PrimaryKey(autoGenerate = true) val index: Int = 0,
    val id: String,
    val title: String,
    val objectNumber: String,
    val date: Long,
    val author: String,
    val hasImage: Boolean,
    val showImage: Boolean,
    @Embedded val image: ArtObjectShortImageEntity? = null,
    val productionPlaces: List<String>? = null
)

data class ArtObjectShortImageEntity(
    val url: String,
    val width: Int,
    val height: Int
)