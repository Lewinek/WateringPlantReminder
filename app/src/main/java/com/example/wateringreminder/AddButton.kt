package com.example.wateringreminder

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wateringreminder.ui.theme.DarkText

@Composable
fun AddButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = CircleShape,
        modifier = modifier.size(48.dp),
        colors = ButtonDefaults.buttonColors(contentColor = DarkText)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}