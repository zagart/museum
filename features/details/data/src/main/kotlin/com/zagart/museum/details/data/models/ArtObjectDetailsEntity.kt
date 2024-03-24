package com.zagart.museum.details.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArtObjectDetailsEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val objectNumber: String,
    val description: String,
    val author: String,
    val hasImage: Boolean,
    val showImage: Boolean,
    val imageUrl: String
)
