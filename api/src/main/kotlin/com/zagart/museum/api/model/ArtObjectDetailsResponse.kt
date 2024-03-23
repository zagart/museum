package com.zagart.museum.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtObjectDetailsResponse(
    val artObject: ArtObjectDto
)
