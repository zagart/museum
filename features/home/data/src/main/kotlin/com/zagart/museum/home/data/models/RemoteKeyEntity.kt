package com.zagart.museum.home.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeyEntity(
    @PrimaryKey
    val artObjectId: String,
    val previousPage: Int?,
    val currentPage: Int,
    val nextPage: Int?,
    val date: Long
)
