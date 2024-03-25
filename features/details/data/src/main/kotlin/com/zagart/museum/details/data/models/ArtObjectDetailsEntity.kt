package com.zagart.museum.details.data.models

import androidx.room.Embedded
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
    @Embedded val image: ArtObjectDetailsImageEntity? = null,
    val productionPlaces: List<String>? = null,
    @Embedded val dating: ArtObjectDetailsDatingEntity? = null,
    val principalMakers: List<ArtObjectDetailsPrincipalMakerEntity>? = null
)