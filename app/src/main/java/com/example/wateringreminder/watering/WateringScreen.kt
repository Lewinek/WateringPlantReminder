package com.example.wateringreminder.watering

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wateringreminder.ui.compose.PlantItem
import com.example.wateringreminder.utils.extensions.toDateDisplay
import com.example.wateringreminder.ui.theme.DarkText
import com.example.wateringreminder.ui.theme.kanit
import org.koin.androidx.compose.koinViewModel

@Composable
fun WateringScreen() {
    val viewModel: WateringViewModel = koinViewModel()
    val state = viewModel.state.value

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
                    Text(
                        text = date.toDateDisplay(), modifier = Modifier
                            .padding(start = 32.dp, top = 16.dp),
                        color = DarkText,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = kanit
                    )
                }
                items(plant) {
                    PlantItem(
                        plant = it.plant,
                        isItWatered = it.isWatered,
                        changeWaterState = { viewModel.changeWaterState(it) }
                    )
                }
            }
        }
    }
}