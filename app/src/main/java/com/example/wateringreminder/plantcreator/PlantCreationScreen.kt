package com.example.wateringreminder.plantcreator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wateringreminder.AddButton
import com.example.wateringreminder.DayButton
import com.example.wateringreminder.LabelTextField
import com.example.wateringreminder.R
import com.example.wateringreminder.ui.theme.DarkText
import com.example.wateringreminder.ui.theme.LightBlue
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlantCreationScreen() {

    val viewModel: PlantCreationViewModel = koinViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = LightBlue,
                    RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .weight(1f)
                .fillMaxSize(),
            Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .weight(1f),
            Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LabelTextField("Name", onValueChange = { viewModel.updatePlantName(it) })
                LabelTextField("Location", onValueChange = { viewModel.updatePlantLocation(it) })
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Start)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = "Water days",
                        color = DarkText,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(7) { index ->
                        DayButton(number = index.plus(1).toString()) {}
                    }
                }
                AddButton(modifier = Modifier, onClick = { viewModel.createPlant() })
                Spacer(modifier = Modifier.padding(bottom = 16.dp))
            }
        }
    }
}

@Preview
@Composable
fun PlantCreationScreenPreview() {
    PlantCreationScreen()
}