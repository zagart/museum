package com.zagart.museum.details.presentation.models

data class DetailsUiAuthor(
    val name: String = "",
    val placeOfBirth: String = "",
    val dateOfBirth: String = "",
    val dateOfDeath: String = "",
    val placeOfDeath: String = "",
    val occupation: List<String> = listOf(),
    val roles: String = "",
    val nationality: String = "",
    val biography: String = "",
    val productionPlaces: List<String> = listOf(),
    val qualification: String = ""
)