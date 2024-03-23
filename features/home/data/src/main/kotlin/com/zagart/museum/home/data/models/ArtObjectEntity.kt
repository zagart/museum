package com.zagart.museum.home.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArtObjectEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val page: Int
)