package com.example.bookstorecompose.ui.add_book_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookstorecompose.ui.theme.ButtonColor

/**
 * Созадние / оформление Выпадающего меню категорий
 */
@Composable
fun RoundedCornerDropDownMenu(
    onOptionSelected: (String) -> Unit  //для передачи того, что выбрали
) {
    val expanded = remember {
        mutableStateOf(false)
    }

    val selectedOption = remember {
        mutableStateOf("Bestsellers")
    }

    val categoriesList = listOf(
        "Fantasy",
        "Drama",
        "Bestsellers"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = ButtonColor,
                shape = RoundedCornerShape(25.dp)
            )
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White)
            .clickable {
                expanded.value = true
            }
            .padding(15.dp)
    ) {
        Text(text = selectedOption.value)
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false   // меню закроется, если нажмём около него
            }
        ) {
            categoriesList.forEach { option ->
                DropdownMenuItem(text = {
                    Text(text = option)
                }, onClick = {
                    selectedOption.value = option  //чтобы увидеть Что мы выбрали
                    expanded.value = false   // после выдора меню закроется
                    onOptionSelected(option)  //передаём эту ф-ю
                })
            }
        }
    }
}