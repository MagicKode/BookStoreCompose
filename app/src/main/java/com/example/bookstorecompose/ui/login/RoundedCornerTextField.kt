package com.example.bookstorecompose.ui.login

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookstorecompose.ui.theme.ButtonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedCornerTextField(
    maxLines: Int = 1,
    singleLine: Boolean = true,
    text: String,
    label: String,
    onValueChange: (String) -> Unit

) {
    TextField(
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, ButtonColor, RoundedCornerShape(25.dp)),
        label = {
            Text(text = label, color = Color.Gray)
        },
        singleLine = singleLine,
        maxLines = maxLines
    )
}