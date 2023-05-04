package com.example.wateringreminder.plantcreator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import com.example.wateringreminder.AddButton
import com.example.wateringreminder.DayButton
import com.example.wateringreminder.LabelTextField
import com.example.wateringreminder.R
import com.example.wateringreminder.ui.theme.DarkText
import com.example.wateringreminder.ui.theme.LightBlue
import com.example.wateringreminder.watering.WateringUiState
import org.koin.androidx.compose.koinViewModel


@Composable
fun PlantCreationScreen(onNavigateToMyPlants: () -> Unit) {
    val viewModel: PlantCreationViewModel = koinViewModel()
    val uiState: State<WateringUiState> = viewModel.uiState.collectAsState()
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = Unit, block = {
        viewModel.events.flowWithLifecycle(lifecycle).collect() {
            when (it) {
                UiEvents.FinishScreen -> onNavigateToMyPlants()
                UiEvents.ShowSnackbar -> {}
            }
        }
    })

    PlantCreationScreenContent(
        viewModel::updatePlantName,
        viewModel::updatePlantLocation,
        viewModel::updateSelectedDay,
        viewModel::createPlant,
        uiState.value
    )
}

@Composable
fun PlantCreationScreenContent(
    updatePlantName: (String) -> Unit,
    updatePlantLocation: (String) -> Unit,
    updateSelectedDay: (String) -> Unit,
    createPlant: () -> Unit,
    uiState: WateringUiState
) {
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
                LabelTextField(
                    label = stringResource(R.string.plant_name_label),
                    onValueChange = { updatePlantName(it) },
                    isError = uiState.showNameError,
                    errorMsg = stringResource(id = uiState.errorNameMsg)
                )
                LabelTextField(
                    stringResource(R.string.plant_location_label),
                    onValueChange = { updatePlantLocation(it) },
                    false,
                    ""
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Start)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.water_days_label),
                        color = DarkText,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
                LazyRow(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.daysIntervalForSelection) { day ->
                        DayButton(
                            day = day,
                            selectedDay = uiState.selectedDay,
                            onClick = { updateSelectedDay(it) }
                        )
                    }
                }
                AddButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = { createPlant() }
                )
            }
        }
    }
}

@Preview
@Composable
fun PlantCreationScreenContentPreview() {
    PlantCreationScreenContent(
        updatePlantName = {},
        updatePlantLocation = {},
        updateSelectedDay = {},
        createPlant = { /*TODO*/ },
        uiState = WateringUiState()
    )
}

@Preview
@Composable
fun PlantCreationScreenPreview() {
    PlantCreationScreen({})
}

sealed interface UiEvents {
    object ShowSnackbar : UiEvents
    object FinishScreen : UiEvents
}