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
import com.example.wateringreminder.compose.PlantItem
import com.example.wateringreminder.ui.theme.DarkText
import org.koin.androidx.compose.koinViewModel

@Composable
fun WateringScreen() {
    val viewModel: WateringViewModel = koinViewModel()
    val state = viewModel.state.value

    Column(modifier = Modifier.background(color = Color.White)) {
        Text(
            text = "Water Today",
            modifier = Modifier
                .padding(16.dp),
            color = DarkText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 8.dp),
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
        ) {
            state.plants?.let {
                it.toList().forEach { (date, plant) ->
                    item {
                        Text(text = date, Modifier.background(color = Color.Red))
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
}