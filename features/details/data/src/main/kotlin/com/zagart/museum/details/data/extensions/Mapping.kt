package com.zagart.museum.details.data.extensions

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.details.data.models.ArtObjectDetailsDatingEntity
import com.zagart.museum.details.data.models.ArtObjectDetailsEntity
import com.zagart.museum.details.data.models.ArtObjectDetailsImageEntity
import com.zagart.museum.details.data.models.ArtObjectDetailsPrincipalMakerEntity
import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.domain.models.ArtObjectDating
import com.zagart.museum.home.domain.models.ArtObjectImage
import com.zagart.museum.home.domain.models.ArtObjectPrincipalMaker

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
        },
        productionPlaces = productionPlaces,
        dating = dating?.let { dating ->
            ArtObjectDating(
                presentingDate = dating.presentingDate,
                sortingDate = dating.sortingDate,
                period = dating.period,
                yearEarly = dating.yearEarly,
                yearLate = dating.yearLate
            )
        },
        principalMakers = principalMakers?.map { entity ->
            ArtObjectPrincipalMaker(
                name = entity.name ?: "",
                unFixedName = entity.unFixedName ?: "",
                placeOfBirth = entity.placeOfBirth ?: "",
                dateOfBirth = entity.dateOfBirth ?: "",
                dateOfBirthPrecision = entity.dateOfBirthPrecision ?: "",
                dateOfDeath = entity.dateOfDeath ?: "",
                dateOfDeathPrecision = entity.dateOfDeathPrecision ?: "",
                placeOfDeath = entity.placeOfDeath ?: "",
                occupation = entity.occupation ?: listOf(),
                roles = entity.roles ?: listOf(),
                nationality = entity.nationality ?: "",
                biography = entity.biography ?: "",
                productionPlaces = entity.productionPlaces ?: listOf(),
                qualification = entity.qualification ?: "",
                labelDesc = entity.labelDesc ?: ""
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
        },
        productionPlaces = productionPlaces,
        dating = dating?.let { dating ->
            ArtObjectDating(
                presentingDate = dating.presentingDate,
                sortingDate = dating.sortingDate,
                period = dating.period,
                yearEarly = dating.yearEarly,
                yearLate = dating.yearLate
            )
        },
        principalMakers = principalMakers?.map { entity ->
            ArtObjectPrincipalMaker(
                name = entity.name,
                unFixedName = entity.unFixedName,
                placeOfBirth = entity.placeOfBirth,
                dateOfBirth = entity.dateOfBirth,
                dateOfBirthPrecision = entity.dateOfBirthPrecision,
                dateOfDeath = entity.dateOfDeath,
                dateOfDeathPrecision = entity.dateOfDeathPrecision,
                placeOfDeath = entity.placeOfDeath,
                occupation = entity.occupation,
                roles = entity.roles,
                nationality = entity.nationality,
                biography = entity.biography,
                productionPlaces = entity.productionPlaces,
                qualification = entity.qualification,
                labelDesc = entity.labelDesc
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
        },
        productionPlaces = productionPlaces,
        dating = dating?.let { dating ->
            ArtObjectDetailsDatingEntity(
                presentingDate = dating.presentingDate,
                sortingDate = dating.sortingDate,
                period = dating.period,
                yearEarly = dating.yearEarly,
                yearLate = dating.yearLate
            )
        },
        principalMakers = principalMakers?.map { entity ->
            ArtObjectDetailsPrincipalMakerEntity(
                name = entity.name?:"",
                unFixedName = entity.unFixedName?:"",
                placeOfBirth = entity.placeOfBirth?:"",
                dateOfBirth = entity.dateOfBirth?:"",
                dateOfBirthPrecision = entity.dateOfBirthPrecision?:"",
                dateOfDeath = entity.dateOfDeath?:"",
                dateOfDeathPrecision = entity.dateOfDeathPrecision?:"",
                placeOfDeath = entity.placeOfDeath?:"",
                occupation = entity.occupation?: listOf(),
                roles = entity.roles?: listOf(),
                nationality = entity.nationality?:"",
                biography = entity.biography?:"",
                productionPlaces = entity.productionPlaces?: listOf(),
                qualification = entity.qualification?:"",
                labelDesc = entity.labelDesc?:""
            )
        }
    )
}