package com.zagart.museum.details.presentation.extensions

import com.zagart.museum.details.presentation.models.DetailsUiAuthor
import com.zagart.museum.details.presentation.models.DetailsUiImage
import com.zagart.museum.details.presentation.models.DetailsUiModel
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.models.ArtObjectPrincipalMaker
import com.zagart.museum.home.presentation.extensions.formatProductionPlaces

fun ArtObject.domainAsUiModel(): DetailsUiModel {
    return DetailsUiModel(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = description ?: "",
        author = principalOrFirstMaker,
        image = image?.let { image ->
            DetailsUiImage(
                url = image.url,
                height = image.height,
                width = image.width
            )
        },
        metadata = formatMetadata(),
        authors = principalMakers?.map { domainModel ->
            DetailsUiAuthor(
                name = domainModel.name,
                placeOfBirth = domainModel.placeOfBirth,
                dateOfBirth = domainModel.dateOfBirth,
                dateOfDeath = domainModel.dateOfDeath,
                placeOfDeath = domainModel.placeOfDeath,
                occupation = domainModel.occupation,
                roles = domainModel.formatRoles(),
                nationality = domainModel.nationality,
                biography = domainModel.biography,
                productionPlaces = domainModel.productionPlaces,
                qualification = domainModel.qualification
            )
        }
    )
}

private fun ArtObject.formatMetadata(): String {
    return "${productionPlaces?.formatProductionPlaces()}${formatDating()}"
}

private fun ArtObject.formatDating(): String {
    return if (dating == null || dating?.presentingDate == null) {
        ""
    } else {
        " (${dating?.presentingDate})"
    }
}

private fun ArtObjectPrincipalMaker.formatRoles(): String {
    return roles.joinToString(", ")
}
