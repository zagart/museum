package com.zagart.museum.home.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArtObjectShortEntity(
    @PrimaryKey(autoGenerate = true) val index: Int = 0,
    val id: String,
    val title: String,
    val objectNumber: String,
    val date: Long
)