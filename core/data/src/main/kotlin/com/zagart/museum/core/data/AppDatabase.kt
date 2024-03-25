package com.zagart.museum.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zagart.museum.core.data.converters.Converters
import com.zagart.museum.details.data.daos.ArtObjectDetailsDao
import com.zagart.museum.details.data.models.ArtObjectDetailsEntity
import com.zagart.museum.home.data.daos.ArtObjectShortDao
import com.zagart.museum.home.data.models.ArtObjectShortEntity

@Database(
    entities = [
        ArtObjectShortEntity::class,
        ArtObjectDetailsEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [Converters::class])
/**
 * General rule for DAO implementations: in case you need updates, use Flow<*> return type.
 * In case you need synchronous access with (i.e. single()), use direct data type.
 */
abstract class AppDatabase : RoomDatabase() {

    abstract fun artObjectShortDao(): ArtObjectShortDao

    abstract fun artObjectDetailsDao(): ArtObjectDetailsDao
}