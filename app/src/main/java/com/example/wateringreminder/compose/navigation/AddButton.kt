package com.example.wateringreminder.compose.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wateringreminder.R
import com.example.wateringreminder.ui.theme.DarkText
import com.example.wateringreminder.ui.theme.LightText

@Composable
fun AddButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = DarkText)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = LightText
        )
        Text(text = "Add Plant", color = LightText)
    }
}

@Preview
@Composable
fun AddButtonPreview() {
    AddButton(modifier = Modifier) {}
}