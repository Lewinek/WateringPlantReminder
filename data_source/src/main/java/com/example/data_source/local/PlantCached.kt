package com.example.data_source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data_source.Plant

@Entity
data class PlantCached(
    @PrimaryKey val id: Int? = null,
    val name: String,
) {
    constructor(plant: Plant) : this(
        plant.id,
        plant.name,
    )

    fun toPlant() = Plant(
        id = id,
        name = name
    )
}