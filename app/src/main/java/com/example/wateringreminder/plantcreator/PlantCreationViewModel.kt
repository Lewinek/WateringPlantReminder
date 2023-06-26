package com.example.wateringreminder.plantcreator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.EventRepository
import com.example.data_source.Plant
import com.example.data_source.PlantRepository
import com.example.data_source.local.Event
import com.example.data_source.local.PlantCached
import com.example.wateringreminder.utils.constants.Constants
import com.example.wateringreminder.utils.validators.EmptyTextFieldValidator
import com.example.wateringreminder.watering.WateringUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class PlantCreationViewModel(
    private val plantRepository: PlantRepository,
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WateringUiState())
    val uiState: StateFlow<WateringUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UiEvents>()
    val events = _events.asSharedFlow()


    fun createPlant() {
        if (!_uiState.value.showNameError) {
            viewModelScope.launch {
                val plantId = plantRepository.insertPlant(
                    Plant(
                        name = uiState.value.plantName,
                        location = Constants.LOCATION
                    )
                )
                val plant = plantRepository.getPlantById(plantId.toInt())
                eventRepository.insertEvent(createEvent(plant))
                _events.emit(UiEvents.FinishScreen)
            }
        }
    }

    fun createEvent(plant: PlantCached): Event {
        return Event(
            wateringDate = LocalDate.now(),
            recurringInterval = 1,
            plantCached = plant,
            isWatered = false
        )
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
        _uiState.update { uiState -> uiState.copy(location = location) }
    }

    fun updateSelectedDay(selectedDay: String) {
        _uiState.update { uiState -> uiState.copy(selectedDay = selectedDay) }
    }
}