package com.zagart.museum.home.domain.models

data class ArtObject(
    val id: String,
    val title: String,
    val objectNumber: String,
    val principalOrFirstMaker: String,
    val description: String?,
    val withAuthorHeader: Boolean = false,
    val hasImage: Boolean,
    val showImage: Boolean,
    val imageUrl: String
)
