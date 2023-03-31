package com.example.wateringreminder.plantlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wateringreminder.PlantsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlantsScreen(

) {
    val viewModel: PlantsViewModel = koinViewModel()
    val state = viewModel.state.value

    LazyColumn() {
        items(state.plants) { plants ->
            Column {
                Text(
                    text = plants.name,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.White
                )
                Divider(color = Color.LightGray)
            }
        }
    }
}

@Preview
@Composable
fun PlantsScreenPreview() {
    PlantsScreen()
}