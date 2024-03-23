package com.zagart.museum.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zagart.museum.details.data.daos.ArtObjectDetailsDao
import com.zagart.museum.details.data.models.ArtObjectDetailsEntity
import com.zagart.museum.home.data.daos.ArtObjectShortDao
import com.zagart.museum.home.data.daos.RemoteKeyDao
import com.zagart.museum.home.data.models.ArtObjectShortEntity
import com.zagart.museum.home.data.models.RemoteKeyEntity

@Database(
    entities = [
        ArtObjectShortEntity::class,
        ArtObjectDetailsEntity::class,
        RemoteKeyEntity::class
    ],
    version = 1,
    exportSchema = false
)
/**
 * General rule for DAO implementations: in case you need updates, use Flow<*> return type.
 * In case you need synchronous access with (i.e. single()), use direct data type.
 */
abstract class AppDatabase : RoomDatabase() {

    abstract fun artObjectShortDao(): ArtObjectShortDao

    abstract fun artObjectDetailsDao(): ArtObjectDetailsDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}