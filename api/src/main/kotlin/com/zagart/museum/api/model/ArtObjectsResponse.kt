package com.zagart.museum.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtObjectsResponse(
    val artObjects: List<ArtObjectDto>
)
