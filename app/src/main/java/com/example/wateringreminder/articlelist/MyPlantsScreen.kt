package com.example.wateringreminder.articlelist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wateringreminder.R
import com.example.wateringreminder.ui.theme.DarkText

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPlantsScreen(onNavigateToPlantCreator: () -> Unit) {
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
    ) {}
}

@Preview
@Composable
fun MyPlantsScreenPreview() {
    MyPlantsScreen {}
}