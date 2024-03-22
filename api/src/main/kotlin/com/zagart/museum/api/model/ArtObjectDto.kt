package com.zagart.museum.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtObjectDto(
    val id: String,
    val objectNumber: String,
    val title: String,
    val hasImage: Boolean,
    val principalOrFirstMaker: String,
    val longTitle: String,
    val showImage: Boolean,
    val permitDownload: Boolean
)
