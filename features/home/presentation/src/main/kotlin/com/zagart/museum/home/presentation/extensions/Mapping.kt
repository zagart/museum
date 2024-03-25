package com.zagart.museum.home.presentation.extensions

import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.presentation.models.HomeScreenImageUi
import com.zagart.museum.home.presentation.models.HomeScreenModelUi

fun ArtObject.toUiModel(): HomeScreenModelUi {
    return HomeScreenModelUi(
        id = id,
        title = title,
        objectNumber = objectNumber,
        author = principalOrFirstMaker,
        withAuthorHeader = withAuthorHeader,
        image = image?.let { image ->
            HomeScreenImageUi(
                url = image.url,
                width = image.width,
                height = image.height
            )
        },
        productionPlaces = productionPlaces?.formatProductionPlaces()
    )
}

fun List<String>.formatProductionPlaces(): String {
    return toSet().joinToString(separator = ", ") { it.replace("? ", "").replace("unknown", "") }
}