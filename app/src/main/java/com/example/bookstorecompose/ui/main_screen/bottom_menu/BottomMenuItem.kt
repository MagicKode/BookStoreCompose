package com.example.bookstorecompose.ui.main_screen.bottom_menu

import com.example.bookstorecompose.R

/**
 * создание элементов Нижнего меню ДОМ/Избранные/Настройки
 */
sealed class BottomMenuItem(
    val route: String,
    val title: String,
    val iconId: Int
) {
    object Home : BottomMenuItem(  //создаём объект  ДОМ, наследуясь от BottomMenuItem
        route = "",
        title = "Home",
        iconId = R.drawable.ic_home
    )
    object Favorites : BottomMenuItem(  //создаём объект ИЗБРАННЫЕ, наследуясь от BottomMenuItem
        route = "",
        title = "Favorites",
        iconId = R.drawable.ic_favorite
    )
    object Settings : BottomMenuItem(  //создаём объект НАСТРОЙКИ, наследуясь от BottomMenuItem
        route = "",
        title = "Settings",
        iconId = R.drawable.ic_settings
    )
}
