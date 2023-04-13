package com.example.wateringreminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wateringreminder.ui.theme.BackgroundGrey
import com.example.wateringreminder.ui.theme.BorderTextField
import com.example.wateringreminder.ui.theme.DarkText

@Composable
fun LabelTextField(
    label: String,
    onValueChange: (String) -> Unit
) {

    var value by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.White)
    ) {
        Text(
            text = label,
            color = DarkText,
            modifier = Modifier.padding(start = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(value.text)
                value = it
            },
            textStyle = TextStyle(color = DarkText),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DarkText,
                unfocusedBorderColor = BorderTextField,
                backgroundColor = BackgroundGrey
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun LabelTextFieldPreview() {
    LabelTextField("", onValueChange = {})
}