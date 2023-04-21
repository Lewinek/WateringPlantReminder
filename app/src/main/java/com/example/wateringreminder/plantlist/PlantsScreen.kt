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
import androidx.compose.ui.unit.dp
import com.example.wateringreminder.PlantItem
import com.example.wateringreminder.PlantsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlantsScreen(
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(
                top = 16.dp,
            )
        ) {
            items(state.plants) { plant ->
                PlantItem(plant)
            }
        }
    }
}