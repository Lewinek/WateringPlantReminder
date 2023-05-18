package com.example.data_source.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.data_source.Plant

@Entity(tableName = "plant", indices = [Index(value = ["name"], unique = true)])
data class PlantCached(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val location: String? = null
) {
    constructor(plant: Plant) : this(
        plant.id,
        plant.name,
        plant.location
    )

    fun toPlant() = Plant(
        id = id,
        name = name,
        location = location
    )
}