package com.example.wateringreminder.watering

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wateringreminder.ui.compose.PlantItem
import com.example.wateringreminder.ui.theme.DarkText
import com.example.wateringreminder.ui.theme.Grey
import com.example.wateringreminder.ui.theme.kanit
import com.example.wateringreminder.utils.extensions.displayRemainingPlantsToWater
import com.example.wateringreminder.utils.extensions.isToday
import com.example.wateringreminder.utils.extensions.toDateDisplay
import org.koin.androidx.compose.koinViewModel

@Composable
fun WateringScreen() {
    val viewModel: WateringViewModel = koinViewModel()
    val uiState: State<PlantsState> = viewModel.state.collectAsState()

    WateringScreenContent(
        uiState.value,
        viewModel::changeWaterState
    )
}

@Composable
fun WateringScreenContent(
    state: PlantsState,
    changeWaterState: (WaterThePlantNotification) -> Unit
) {
    Column(modifier = Modifier.background(color = Color.White)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 8.dp),
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
        ) {
            state.plants.toList().forEach { (date, plant) ->
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = date.toDateDisplay(),
                            modifier = Modifier.padding(start = 32.dp),
                            color = DarkText,
                            fontSize = if (date.isToday()) 28.sp else 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = kanit
                        )
                        if (date.isToday()) {
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = plant.displayRemainingPlantsToWater(),
                                modifier = Modifier.padding(end = 32.dp, bottom = 4.dp),
                                color = Grey,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = kanit
                            )
                        }
                    }
                }
                items(plant) {
                    PlantItem(
                        plant = it.plant,
                        isItWatered = it.isWatered,
                        changeWaterState = { changeWaterState(it) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun WateringScreenContentPreview() {
    WateringScreenContent(PlantsState(), {})
}