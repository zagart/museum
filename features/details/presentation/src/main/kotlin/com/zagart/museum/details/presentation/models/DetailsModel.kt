package com.zagart.museum.details.presentation.models

data class DetailsModel(
    val id: String,
    val title: String,
    val objectNumber: String,
    val description: String,
    val author: String,
    val image: DetailsImage? = null
)

data class DetailsImage(
    val url: String,
    val height: Int,
    val width: Int
)