package com.zagart.museum.api

import com.zagart.museum.api.model.ArtObjectsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * API key was left unsecured so that during review it was easy to launch and test app.
 * Github repository will be private, so only invited people will have access.
 */
private const val API_KEY = "0fiuZFh4"
const val PAGE_SIZE = 20

interface MuseumApi {

    @GET("api/en/collection")
    suspend fun requestArtObjects(
        @Query("key") apiKey: String = API_KEY,
        @Query("ps") pageSize: Int = PAGE_SIZE,
        @Query("p") page: Int
    ): ArtObjectsResponse

    companion object {
        val CACHE_EXPIRE_TIME = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
    }
}