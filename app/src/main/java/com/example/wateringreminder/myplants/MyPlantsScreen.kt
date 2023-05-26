@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.wateringreminder.myplants

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.wateringreminder.ui.compose.MyPlantItem
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPlantsScreenContent(
    onNavigateToPlantCreator: () -> Unit,
    uiState: MyPlantsUiState,
    removePlant: (Plant) -> Unit,
) {
    Column(modifier = Modifier.background(color = Color.White)) {
        Row() {
            Text(
                text = stringResource(R.string.label_my_plants),
                modifier = Modifier
                    .padding(16.dp),
                color = DarkText,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onNavigateToPlantCreator,
                colors = ButtonDefaults.buttonColors(containerColor = DarkText),
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "",
                    tint = Color.White
                )
                Text(
                    text = stringResource(R.string.label_plant),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        LazyColumn() {
            items(
                items = uiState.plants,
                key = { it.id!! },
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
                        modifier = Modifier.animateItemPlacement(),
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