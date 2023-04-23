package com.example.wateringreminder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data_source.Plant
import com.example.wateringreminder.ui.theme.Grey
import com.example.wateringreminder.ui.theme.LightBlue
import com.example.wateringreminder.ui.theme.LightText

@Composable
fun PlantItem(plant: Plant) {

    var isItWatered by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = cardColors(containerColor = if (isItWatered) LightBlue else LightText),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.plant),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.5f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.Top)
                    .padding(top = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = plant.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.water_container),
                        contentDescription = "",
                        tint = if (isItWatered) Color.White else Grey
                    )
                    Text(
                        text = "~ 150 ml",
                        color = if (isItWatered) Color.White else Grey
                    )
                }
            }
            Button(
                onClick = {
                    isItWatered = !isItWatered
                },
                shape = CircleShape,
                modifier = Modifier
                    .size(48.dp),
                contentPadding = PaddingValues(1.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isItWatered) LightText else Color.White)
            ) {
                Icon(
                    painter = if (isItWatered)
                        painterResource(id = R.drawable.check)
                    else
                        painterResource(id = R.drawable.water_drop),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp),
                    tint = if (isItWatered) Color.White else LightBlue
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview
@Composable
fun PlantItemPreview() {
    PlantItem(Plant(name = "Sinocrassula yunnanensis"))
}