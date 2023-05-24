package com.example.wateringreminder.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelTextField(
    label: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMsg: String
) {

    var value by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
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
                value = it
                onValueChange(value.text)
            },
            textStyle = TextStyle(color = DarkText),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DarkText,
                unfocusedBorderColor = BorderTextField,
                containerColor = BackgroundGrey
            ),
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )
    }
}

@Preview
@Composable
fun LabelTextFieldPreview() {
    LabelTextField("", onValueChange = {}, false, "")
}