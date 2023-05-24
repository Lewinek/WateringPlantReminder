package com.example.wateringreminder.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data_source.Plant
import com.example.wateringreminder.R
import com.example.wateringreminder.ui.theme.LightBlue

@Composable
fun MyPlantItem(plant: Plant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue),

        ) {
        Column {
            Row(
                modifier = Modifier
                    .background(color = LightBlue)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plant),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(0.4f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                )
                Column(
                    modifier = Modifier
                        .background(color = LightBlue)
                        .weight(1f)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = plant.name,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.label_location),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.placeholder_kitchen),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            LazyRow {
                items(1) {
                    DetailLabel(
                        label = stringResource(R.string.label_water), value = stringResource(
                            R.string.placeholder_everyday
                        ), R.drawable.water_drop
                    )
                    DetailLabel(
                        label = stringResource(R.string.label_temperature), value = stringResource(
                            R.string.placeholder_temperature
                        ), R.drawable.water_drop
                    )
                    DetailLabel(
                        label = stringResource(R.string.label_difficulty), value = stringResource(
                            R.string.placeholder_difficulty
                        ), R.drawable.water_drop
                    )
                }
            }
        }
    }
}