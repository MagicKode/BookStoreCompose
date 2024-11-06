package com.example.bookstorecompose.ui.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    ModalNavigationDrawer(  // создание выдвижного меню
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(0.7f),
        drawerContent = {
            Column(Modifier.fillMaxSize()) {
                DrawerHeader()
                DrawerBody()
            }
        }
    ) {

    }
}