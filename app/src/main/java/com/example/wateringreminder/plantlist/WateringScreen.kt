package com.example.wateringreminder.plantlist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wateringreminder.PlantItem
import com.example.wateringreminder.PlantsViewModel
import com.example.wateringreminder.ui.theme.DarkText
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WateringScreen(
    onNavigateToPlantCreator: () -> Unit
) {
    val viewModel: PlantsViewModel = koinViewModel()
    val state = viewModel.state.value
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToPlantCreator,
                modifier = Modifier.padding(40.dp)
            ) {}
        },
        containerColor = Color.White
    ) {
        Column() {
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
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(state.plants) { plant ->
                    PlantItem(plant)
                }
            }
        }
    }
}