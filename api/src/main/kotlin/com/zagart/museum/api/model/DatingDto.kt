package com.zagart.museum.api.model

import kotlinx.serialization.Serializable

@Serializable
data class DatingDto(
    val presentingDate: String?,
    val sortingDate: Int,
    val period: Int,
    val yearEarly: Int,
    val yearLate: Int
)
