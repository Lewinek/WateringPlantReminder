package com.example.wateringreminder.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data_source.Plant
import com.example.wateringreminder.R
import com.example.wateringreminder.ui.theme.LightBlue
import com.example.wateringreminder.ui.theme.kanit

@Composable
fun MyPlantItem(plant: Plant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue),
    ) {
        Column() {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Empty text for vertical alignment purposes.
                Text("\n \n",)
                Text(
                    text = plant.name,
                    maxLines = 2,
                    modifier = Modifier.align(Alignment.TopStart),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = kanit,
                )
            }
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plant),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
                Column() {
                    CharacteristicLabel(
                        label = stringResource(R.string.label_water),
                        value = stringResource(R.string.placeholder_difficulty),
                        icon = R.drawable.water_drop
                    )
                    CharacteristicLabel(
                        label = stringResource(R.string.label_light),
                        value = stringResource(R.string.placeholder_medium),
                        icon = R.drawable.sunny
                    )
                    CharacteristicLabel(
                        label = stringResource(R.string.label_temperature),
                        value = stringResource(R.string.placeholder_temperature),
                        icon = R.drawable.thermometer
                    )
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 16.dp))
        }
    }
}
//        ) {
//        Column {
//            Row(
//                modifier = Modifier
//                    .background(color = LightBlue)
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.plant),
//                    contentDescription = "",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .clip(RoundedCornerShape(12.dp)),
//                )
//                Column(
//                    modifier = Modifier
//                        .background(color = LightBlue)
//                        .weight(1f)
//                        .padding(top = 8.dp)
//                ) {
//                    Text(
//                        text = plant.name,
//                        color = Color.White,
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(
//                        text = stringResource(R.string.label_location),
//                        color = Color.White,
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        text = stringResource(R.string.placeholder_kitchen),
//                        color = Color.White,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//        }
//    }
//}

@Preview
@Composable
fun MyPlantItemPreview() {
    MyPlantItem(plant = Plant(id = 1, name = "Fiołek", location = "Kitchen"))
}