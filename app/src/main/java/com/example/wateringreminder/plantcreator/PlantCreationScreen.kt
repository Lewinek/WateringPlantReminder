package com.example.wateringreminder.plantcreator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.wateringreminder.LabelTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlantCreationScreen() {

    val viewModel: PlantCreationViewModel = koinViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LabelTextField("Name", onValueChange = { viewModel.updatePlantName(it) })
        Button(
            onClick = { }
        ) {
            Text("Add")
        }
    }
}

@Preview
@Composable
fun PlantCreationScreenPreview() {
    PlantCreationScreen()
}