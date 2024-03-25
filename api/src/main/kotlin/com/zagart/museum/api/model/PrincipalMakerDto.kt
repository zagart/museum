package com.zagart.museum.api.model

import kotlinx.serialization.Serializable

@Serializable
data class PrincipalMakerDto(
    val name: String?,
    val unFixedName: String?,
    val placeOfBirth: String?,
    val dateOfBirth: String?,
    val dateOfBirthPrecision: String?,
    val dateOfDeath: String?,
    val dateOfDeathPrecision: String?,
    val placeOfDeath: String?,
    val occupation: List<String>?,
    val roles: List<String>?,
    val nationality: String?,
    val biography: String?,
    val productionPlaces: List<String>?,
    val qualification: String?,
    val labelDesc: String?
)
