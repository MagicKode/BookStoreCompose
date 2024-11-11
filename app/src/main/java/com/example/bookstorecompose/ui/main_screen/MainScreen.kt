package com.example.bookstorecompose.ui.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bookstorecompose.ui.login.data.MainScreenDataObject
import com.example.bookstorecompose.ui.main_screen.bottom_menu.BottomMenu

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navData: MainScreenDataObject) {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    ModalNavigationDrawer(  // создание выдвижного меню
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader(navData.email)
                DrawerBody()
            }
        }
    ) {
        Scaffold(
            Modifier.fillMaxSize(),
            bottomBar = {
                BottomMenu()
            }
        ) {

        }
    }
}