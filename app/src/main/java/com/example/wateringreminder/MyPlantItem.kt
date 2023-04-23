package com.example.wateringreminder

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wateringreminder.ui.theme.LightBlue

@Composable
fun MyPlantItem() {
    Text("My Plants")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue),

        ) {
        Column() {
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
                        "Calathea Orbifolia",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Location",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Kitchen",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            LazyRow(){
                items(2){
                    DetailLabel(label = "WATER", value = "1/week", R.drawable.water_drop)
                    DetailLabel(label = "TEMPERATURE", value = "15-24C", R.drawable.water_drop)
                    DetailLabel(label = "DIFFICULTY", value = "easy", R.drawable.water_drop)
                }
            }
        }
    }
}

@Preview
@Composable
fun MyPlantPreview() {
    MyPlantItem()
}
