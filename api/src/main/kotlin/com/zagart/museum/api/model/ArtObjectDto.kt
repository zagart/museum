package com.zagart.museum.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtObjectDto(
    val id: String,
    val title: String,
    val objectNumber: String,
    val hasImage: Boolean,
    val showImage: Boolean,
    val webImage: WebImageDto?,
    val description: String? = null,
    val principalOrFirstMaker: String? = null,
    val longTitle: String? = null,
    val permitDownload: Boolean? = null
)
