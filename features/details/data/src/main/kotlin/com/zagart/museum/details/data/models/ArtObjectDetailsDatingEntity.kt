package com.zagart.museum.details.data.models

data class ArtObjectDetailsDatingEntity(
    val presentingDate: String?,
    val sortingDate: Int,
    val period: Int,
    val yearEarly: Int,
    val yearLate: Int
)