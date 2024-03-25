package com.zagart.museum.home.domain.models

data class ArtObjectDating(
    val presentingDate: String?,
    val sortingDate: Int,
    val period: Int,
    val yearEarly: Int,
    val yearLate: Int
)