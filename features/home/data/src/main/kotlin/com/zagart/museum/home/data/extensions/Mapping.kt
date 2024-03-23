package com.zagart.museum.home.data.extensions

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.home.data.models.ArtObjectShortEntity
import com.zagart.museum.home.data.models.RemoteKeyEntity
import com.zagart.museum.home.domain.models.ArtObject

fun List<ArtObjectDto>.dtosAtEntityList(page: Int): List<ArtObjectShortEntity> {
    return map {
        ArtObjectShortEntity(
            id = it.id, title = it.title, objectNumber = it.objectNumber, page = page
        )
    }
}

fun ArtObjectShortEntity.toDomainModel(): ArtObject {
    return ArtObject(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = null
    )
}

fun ArtObjectDto.asRemoteKeyEntity(page: Int, endOfPaginationReached: Boolean): RemoteKeyEntity {
    val previousPage = if (page > 1) page - 1 else null
    val nextPage = if (endOfPaginationReached) null else page + 1

    return RemoteKeyEntity(
        artObjectId = id,
        previousPage = previousPage,
        currentPage = page,
        nextPage = nextPage,
        date = System.currentTimeMillis()
    )
}