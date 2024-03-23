package com.zagart.museum.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtObjectDto(
    val id: String,
    val title: String,
    val objectNumber: String,
    val description: String? = null,
    val hasImage: Boolean? = null,
    val principalOrFirstMaker: String? = null,
    val longTitle: String? = null,
    val showImage: Boolean? = null,
    val permitDownload: Boolean? = null
)
