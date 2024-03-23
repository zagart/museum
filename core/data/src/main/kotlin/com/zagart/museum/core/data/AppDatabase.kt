package com.zagart.museum.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zagart.museum.home.data.daos.ArtObjectDao
import com.zagart.museum.home.data.daos.RemoteKeyDao
import com.zagart.museum.home.data.models.ArtObjectEntity
import com.zagart.museum.home.data.models.RemoteKeyEntity

@Database(
    entities = [ArtObjectEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
/**
 * General rule for DAO implementations: in case you need updates, use Flow<*> return type.
 * In case you need synchronous access with (i.e. single()), use direct data type.
 */
abstract class AppDatabase : RoomDatabase() {

    abstract fun artObjectDao(): ArtObjectDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}