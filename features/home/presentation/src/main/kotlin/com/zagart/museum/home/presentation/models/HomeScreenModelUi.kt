package com.zagart.museum.home.presentation.models

import androidx.compose.runtime.Stable

@Stable
data class HomeScreenModelUi(
    val id: String = "",
    val title: String = "",
    val objectNumber: String = "",
    val author: String = "",
    val withAuthorHeader: Boolean = false,
    val image: HomeScreenImageUi? = null,
    val productionPlaces: String? = null
)