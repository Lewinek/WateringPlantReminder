package com.example.wateringreminder.plantcreator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import com.example.wateringreminder.EmptyTextFieldValidator
import kotlinx.coroutines.launch

class PlantCreationViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private var plantName by mutableStateOf("")
    private var location by mutableStateOf("")
    var isPlantNameCorrect by mutableStateOf(false)
    var dayIndex = mutableStateOf(-1)
    private var numberOfDaysToWatering: Int? = null
        get() {
            return if (dayIndex.value == -1) null else dayIndex.value
        }


    fun createPlant() {
        if (isPlantNameCorrect) {
            viewModelScope.launch {
                plantRepository.insertPlant(
                    Plant(
                        name = plantName,
                        location = location,
                        numberOfDaysToWatering = numberOfDaysToWatering
                    )
                )
            }
        }
    }

    private fun plantNameValid() {
        val namePlantValid = EmptyTextFieldValidator()
        isPlantNameCorrect = !namePlantValid.validate(plantName.trim())
    }

    fun updatePlantName(name: String) {
        plantName = name
        plantNameValid()
    }

    fun updatePlantLocation(location: String) {
        this.location = location
    }

    fun updateIndex(index: Int) {
        this.dayIndex.value = index
    }
}