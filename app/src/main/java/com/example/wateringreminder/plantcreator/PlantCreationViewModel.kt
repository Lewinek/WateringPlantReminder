package com.example.wateringreminder.plantcreator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import com.example.wateringreminder.EmptyTextFieldValidator
import com.example.wateringreminder.watering.WateringUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

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
    val events = MutableSharedFlow<UiEvents>()

    fun createPlant() {
        if (!_uiState.value.showNameError) {
            viewModelScope.launch {
                plantRepository.insertPlant(
                    Plant(
                        name = plantName,
                        location = location,
                        wateringDate = numberOfDaysToWatering?.let { getWateringDay(it) },
                        numberOfDaysToWatering = numberOfDaysToWatering
                    )
                )
            }
        }
    }


    private fun getWateringDay(numberOfDaysToWatering: Int) : String{
        val current = LocalDate.now()
        current.plusDays(numberOfDaysToWatering.toLong())
        return current.toString()
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