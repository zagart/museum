package com.zagart.museum.api

import com.zagart.museum.api.model.ArtObjectsResponse
import retrofit2.http.GET

/**
 * API key was left unsecured so that during review it was easy to launch and test app.
 * Github repository will be private, so only invited people will have access.
 */
private const val API_KEY = "0fiuZFh4"

interface MuseumApi {

    @GET("api/en/collection?key=$API_KEY")
    suspend fun requestArtObjects(): ArtObjectsResponse
}