package com.zagart.museum.home.data.extensions

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.home.data.models.ArtObjectEntity
import com.zagart.museum.home.data.models.RemoteKeyEntity
import com.zagart.museum.home.domain.models.ArtObject

fun List<ArtObjectDto>.dtosAsDomainList(): List<ArtObject> {
    return map { ArtObject(it.id, it.title) }
}

fun List<ArtObjectEntity>.entitiesAsDomainList(): List<ArtObject> {
    return map { it.toDomainModel() }
}

fun List<ArtObjectDto>.dtosAtEntityList(page: Int): List<ArtObjectEntity> {
    return map {
        ArtObjectEntity(
            id = it.id,
            title = it.title,
            page = page
        )
    }
}

fun ArtObjectEntity.toDomainModel(): ArtObject {
    return ArtObject(id, title)
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