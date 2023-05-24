package com.example.wateringreminder.watering

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.EventRepository
import com.example.data_source.local.PlantCached
import kotlinx.coroutines.launch
import java.time.LocalDate

class WateringViewModel(
    private val eventRepository: EventRepository,
    private val useCase: GetPlantNotificationUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PlantsState())
    val state: State<PlantsState> = _state

    init {
        getPlantsThatNeedWatering()
    }

    private fun getPlantsThatNeedWatering() {
        val today = LocalDate.now()
        viewModelScope.launch {
            val plants = useCase().groupBy {
                if (it.date.isBefore(today)) today else it.date
            }
            _state.value = state.value.copy(plants = plants)
        }
    }

    fun changeWaterState(notification: WaterThePlantNotification) {
        viewModelScope.launch {
            if (!notification.date.isAfter(LocalDate.now())) {
                val event = eventRepository.getEvents().findLast { notification.eventId == it.id }
                val newEvent = event?.copy(isWatered = !event.isWatered)
                newEvent?.let { eventRepository.updateEvent(it) }
                getPlantsThatNeedWatering()
            }
        }
    }
}

data class PlantsState(
    val plants: Map<LocalDate, List<WaterThePlantNotification>> = emptyMap()
)

class GetPlantNotificationUseCase(private val repository: EventRepository) {

    val range: Int = 30
    val numbersOfDay: Int = 5
    val repeatedDay = range / numbersOfDay

    suspend operator fun invoke(): List<WaterThePlantNotification> {
        return repository.getEvents().flatMap { event ->
            val repeatedEvents = (1..repeatedDay).map { i ->
                event.copy(
                    wateringDate = event.wateringDate.plusDays(numbersOfDay.toLong() * i),
                    isWatered = false
                )
            }
            listOf(event) + repeatedEvents
        }.map {
            WaterThePlantNotification(
                eventId = it.id!!,
                plant = it.plantCached,
                date = it.wateringDate,
                isWatered = it.isWatered
            )
        }
    }
}

data class WaterThePlantNotification(
    val eventId: Int,
    val plant: PlantCached,
    val date: LocalDate,
    val isWatered: Boolean,
)