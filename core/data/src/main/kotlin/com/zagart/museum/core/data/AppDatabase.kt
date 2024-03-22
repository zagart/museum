package com.zagart.museum.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zagart.museum.home.data.daos.ArtObjectDao
import com.zagart.museum.home.data.models.ArtObjectEntity

@Database(
    entities = [ArtObjectEntity::class],
    version = 1
)
/**
 * General rule for DAO implementations: in case you need updates, use Flow<*> return type.
 * In case you need synchronous access with (i.e. single()), use direct data type.
 */
abstract class AppDatabase : RoomDatabase() {

    abstract fun artObjectDao() : ArtObjectDao
}