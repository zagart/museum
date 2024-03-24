package com.zagart.museum.api.model

import kotlinx.serialization.Serializable

@Serializable
data class WebImageDto(
    val guid: String,
    val offsetPercentageX: Int,
    val offsetPercentageY: Int,
    val width: Int,
    val height: Int,
    val url: String,
)
