package com.example.wateringreminder.myplants

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data_source.Plant
import com.example.wateringreminder.R
import com.example.wateringreminder.ui.compose.MyPlantItem
import com.example.wateringreminder.ui.compose.MyPlantItemBackground
import com.example.wateringreminder.ui.theme.DarkText
import com.example.wateringreminder.ui.theme.kanit
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyPlantsScreen(onNavigateToPlantCreator: () -> Unit) {

    val viewModel: MyPlantsViewModel = koinViewModel()
    val uiState: State<MyPlantsUiState> = viewModel.uiState.collectAsState()
    MyPlantsScreenContent(
        onNavigateToPlantCreator,
        uiState = uiState.value,
        viewModel::removePlant
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyPlantsScreenContent(
    onNavigateToPlantCreator: () -> Unit,
    uiState: MyPlantsUiState,
    removePlant: (Plant) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Row(modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)) {
            Text(
                text = stringResource(R.string.label_my_plants),
                color = DarkText,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = kanit
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onNavigateToPlantCreator,
                colors = ButtonDefaults.buttonColors(containerColor = DarkText),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "",
                    tint = Color.White
                )
                Text(
                    text = stringResource(R.string.label_plant),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = kanit
                )
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(items = uiState.plants, key = { it.id!! }) { item ->
                val currentItem by rememberUpdatedState(item)
                var isItemDismissed by remember {
                    mutableStateOf(false)
                }
                val dismissState = androidx.compose.material.rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd) {
                            isItemDismissed = true
                        }
                        true
                    }
                )
                val emailHeightAnimation by animateDpAsState(
                    targetValue = if (isItemDismissed) 0.dp else 240.dp,
                    animationSpec = tween(delayMillis = 300),
                    finishedListener = {
                        removePlant(currentItem)
                    }
                )
                androidx.compose.material.SwipeToDismiss(
                    state = dismissState,
                    background = {
                        MyPlantItemBackground(
                            modifier = Modifier
                                .height(emailHeightAnimation)
                                .fillMaxWidth(),
                            targetValue = dismissState.targetValue,
                            currentValue = dismissState.currentValue
                        )
                    },
                    directions = setOf(androidx.compose.material.DismissDirection.StartToEnd),
                    dismissThresholds = { androidx.compose.material.FractionalThreshold(0.15f) },
                    dismissContent = {
                        MyPlantItem(
                            plant = item,
                            modifier = Modifier
                                .height(emailHeightAnimation)
                                .fillMaxWidth()
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun MyPlantsScreenContentPreview() {
    MyPlantsScreenContent({}, MyPlantsUiState(), {})
}