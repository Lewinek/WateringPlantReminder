package com.example.wateringreminder.watering

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_source.EventRepository
import com.example.data_source.local.Event
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
        viewModelScope.launch {
            val plants = useCase().groupBy { it.date.toString() }
            _state.value = state.value.copy(plants = plants)
        }
    }

    fun changeWaterState(notification: WaterThePlantNotification) {
        viewModelScope.launch {
            val event = eventRepository.getEvents().findLast { notification.eventId == it.id }
            val newEvent = event?.copy(lastWaterDay = LocalDate.now())
            newEvent?.let { eventRepository.updateEvent(it) }
            getPlantsThatNeedWatering()
        }
    }
}

data class PlantsState(
    val plants: Map<String, List<WaterThePlantNotification>> = emptyMap()
)

class GetPlantNotificationUseCase(private val repository: EventRepository) {

    val range: Int = 30
    val numbersOfDay: Int = 5
    val repeatedDay = range / numbersOfDay

    suspend operator fun invoke(): List<WaterThePlantNotification> {
        return repository.getEvents().flatMap { event ->
            val repeatedEvents = (1..repeatedDay).map { i ->
                event.copy(
                    startDate = event.startDate.plusDays(numbersOfDay.toLong() * i),
                    lastWaterDay = event.lastWaterDay.plusDays(1)
                )
            }
            listOf(event) + repeatedEvents
        }.map {
            WaterThePlantNotification(
                eventId = it.id!!,
                plant = it.plantCached,
                date = it.startDate,
                isWatered = it.lastWaterDay.isEqual(LocalDate.now())
            )
        }
    }
}

data class WaterThePlantNotification(
    val eventId: Int,
    val plant: PlantCached,
    val date: LocalDate,
    val isWatered: Boolean
)