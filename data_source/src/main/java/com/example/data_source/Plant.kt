package com.example.data_source

data class Plant(
    val id: Int? = null,
    val name: String,
    val location: String? = null,
    val wateringDate: String? = null,
    val numberOfDaysToWatering: Int? = null
)