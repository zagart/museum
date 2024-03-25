package com.zagart.museum.details.data.extensions

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.details.data.models.ArtObjectDetailsEntity
import com.zagart.museum.details.data.models.ArtObjectDetailsImageEntity
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.models.ArtObjectImage

fun ArtObjectDto.dtoAsDomainModel(useEnglish: Boolean = true): ArtObject {
    return ArtObject(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = selectDescription(useEnglish),
        principalOrFirstMaker = principalOrFirstMaker ?: "",
        hasImage = hasImage,
        showImage = showImage,
        image = webImage?.let { image ->
            ArtObjectImage(
                url = image.url,
                width = image.width,
                height = image.height
            )
        }
    )
}

private fun ArtObjectDto.selectDescription(useEnglish: Boolean): String {
    return if (useEnglish) {
        plaqueDescriptionEnglish ?: description ?: ""
    } else {
        plaqueDescriptionDutch ?: description ?: ""
    }
}

fun ArtObjectDetailsEntity.entityAsDomainModel(): ArtObject {
    return ArtObject(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = description,
        principalOrFirstMaker = author,
        hasImage = hasImage,
        showImage = showImage,
        image = image?.let { image ->
            ArtObjectImage(
                url = image.url,
                width = image.width,
                height = image.height
            )
        }
    )
}

fun ArtObjectDto.dtoAsEntityModel(): ArtObjectDetailsEntity {
    return ArtObjectDetailsEntity(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = description ?: "",
        author = principalOrFirstMaker ?: "",
        hasImage = hasImage,
        showImage = showImage,
        image = webImage?.let { image ->
            ArtObjectDetailsImageEntity(
                url = image.url,
                width = image.width,
                height = image.height
            )
        }
    )
}