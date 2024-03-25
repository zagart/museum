package com.zagart.museum.details.presentation.models

data class DetailsUiModel(
    val id: String = "",
    val title: String = "",
    val objectNumber: String = "",
    val description: String = "",
    val author: String = "",
    val image: DetailsUiImage? = null,
    val metadata: String? = null,
    val authors: List<DetailsUiAuthor>? = null
)

