@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.wateringreminder.myplants

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data_source.Plant
import com.example.wateringreminder.MyPlantItem
import com.example.wateringreminder.R
import com.example.wateringreminder.ui.theme.DarkText
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

@Composable
fun MyPlantsScreenContent(
    onNavigateToPlantCreator: () -> Unit,
    uiState: MyPlantsUiState,
    removePlant: (Plant) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToPlantCreator,
                modifier = Modifier
                    .padding(40.dp)
                    .clip(RoundedCornerShape(12.dp)),
                containerColor = DarkText
            ) {
                Icon(painter = painterResource(id = R.drawable.add), contentDescription = "")
            }
        }, containerColor = Color.White
    ) {
        LazyColumn() {
            items(
                items = uiState.plants,
                itemContent = { item ->
                    val currentItem by rememberUpdatedState(item)
                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            removePlant(currentItem)
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        background = {},
                        dismissContent = {
                            MyPlantItem(item)
                        }
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun MyPlantsScreenPreview() {
    MyPlantsScreen {}
}