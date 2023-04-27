package com.example.wateringreminder.plantcreator

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import com.example.wateringreminder.watering.WateringUiState
import com.example.wateringreminder.EmptyTextFieldValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantCreationViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WateringUiState())
    val uiState: StateFlow<WateringUiState> = _uiState.asStateFlow()

    private var plantName by mutableStateOf("")
    private var location by mutableStateOf("")
    var isPlantNameCorrect by mutableStateOf(false)
    var dayIndex = mutableStateOf(-1)
    private var numberOfDaysToWatering: Int? = null
        get() {
            return if (dayIndex.value == -1) null else dayIndex.value
        }


    fun createPlant() {
        if (!_uiState.value.showNameError) {
            viewModelScope.launch {
                _uiState.update { uiState ->
                    uiState.copy(
                        goToMyPlantsScreen = true
                    )
                }
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

    private fun validatePlantName(plantName: String) {
        val namePlantValid = EmptyTextFieldValidator()
        val isPlantNameCorrect = !namePlantValid.validate(plantName.trim())
        _uiState.update { uiState ->
            uiState.copy(
                showNameError = isPlantNameCorrect,
                errorNameMsg = namePlantValid.error
            )
        }
    }

    fun updatePlantName(plantName: String) {
        _uiState.update { uiState -> uiState.copy(plantName = plantName) }
        validatePlantName(plantName)
    }

    fun updatePlantLocation(location: String) {
        this.location = location
    }

    fun updateSelectedDay(selectedDay: String) {
        _uiState.update { uiState -> uiState.copy(selectedDay = selectedDay) }
    }
}