package com.zagart.museum.home.data.extensions

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.home.data.models.ArtObjectEntity
import com.zagart.museum.home.domain.models.ArtObject

fun List<ArtObjectDto>.dtosAsDomainList(): List<ArtObject> {
    return map { ArtObject(it.id, it.title) }
}

fun List<ArtObjectEntity>.entitiesAsDomainList(): List<ArtObject> {
    return map { ArtObject(it.id, it.title) }
}

fun List<ArtObjectDto>.dtosAtEntityList(): List<ArtObjectEntity> {
    return map { ArtObjectEntity(it.id, it.title) }
}