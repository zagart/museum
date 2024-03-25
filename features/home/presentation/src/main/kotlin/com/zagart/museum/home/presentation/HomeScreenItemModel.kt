package com.zagart.museum.home.presentation

data class HomeScreenItemModel(
    val id: String,
    val title: String,
    val objectNumber: String,
    val author: String,
    val withAuthorHeader: Boolean = false,
    val image: HomeScreenImage?
)

data class HomeScreenImage(
    val url: String,
    val width: Int,
    val height: Int
)
