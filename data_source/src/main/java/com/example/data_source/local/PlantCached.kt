package com.example.data_source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlantCached(
    @PrimaryKey val id: Int? = null,
    val name: String,
)
