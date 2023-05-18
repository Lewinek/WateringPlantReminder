package com.example.wateringreminder.watering

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
        val eventList = repository.getEvents()
        val repeatedList = mutableListOf<Event>()
        eventList.forEach {
            repeatedList.add(it)
            for (i in 1..repeatedDay) {
                repeatedList.add(it.copy(startDate = it.startDate.plusDays((numbersOfDay * i).toLong())))
            }
        }
        return repeatedList.map {
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