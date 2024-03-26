package com.zagart.museum.core.data.converters

import androidx.room.TypeConverter
import com.zagart.museum.details.data.models.ArtObjectDetailsPrincipalMakerEntity

object Converters {

    @TypeConverter
    fun List<String>.fromList(): String {
        return joinToString("|")
    }

    @TypeConverter
    fun String.fromString(): List<String> {
        return split("|")
    }

    @TypeConverter
    fun List<ArtObjectDetailsPrincipalMakerEntity>.fromEntityList(): String {
        return joinToString("@entities") { entity ->
            listOf(
                entity.name,
                entity.unFixedName,
                entity.placeOfBirth,
                entity.dateOfBirth,
                entity.dateOfBirthPrecision,
                entity.dateOfDeath,
                entity.dateOfDeathPrecision,
                entity.placeOfDeath,
                entity.occupation.joinToString("@values"),
                entity.roles.joinToString("@values"),
                entity.nationality,
                entity.biography,
                entity.productionPlaces.joinToString("@values"),
                entity.qualification,
                entity.labelDesc
            ).joinToString("@fields")
        }
    }

    @TypeConverter
    fun String.fromStringList(): List<ArtObjectDetailsPrincipalMakerEntity> {
        return split("@entities").map { entityAsString ->
            val entityFields = entityAsString.split("@fields")

            ArtObjectDetailsPrincipalMakerEntity(
                entityFields[0],
                entityFields[1],
                entityFields[2],
                entityFields[3],
                entityFields[4],
                entityFields[5],
                entityFields[6],
                entityFields[7],
                entityFields[8].split("@values"),
                entityFields[9].split("@values"),
                entityFields[10],
                entityFields[11],
                entityFields[12].split("@values"),
                entityFields[13],
                entityFields[14]
            )
        }
    }
}