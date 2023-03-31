package com.example.wateringreminder.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wateringreminder.plantcreator.PlantCreationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlantCreationScreen(
    navController: NavController = rememberNavController()
) {

    var viewModel: PlantCreationViewModel = koinViewModel()
    var plantName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = plantName,
            onValueChange = { text ->
                plantName = text
            }
        )
        Button(onClick = { viewModel.createPlant(plantName) }) {

        }
    }
}

@Preview
@Composable
fun PlantCreationScreenPreview() {
    PlantCreationScreen()
}