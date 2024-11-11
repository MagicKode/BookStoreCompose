package com.example.bookstorecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bookstorecompose.ui.add_book_screen.AddBookScreen
import com.example.bookstorecompose.ui.data.AddScreenObject
import com.example.bookstorecompose.ui.login.LoginScreen
import com.example.bookstorecompose.ui.login.data.LoginScreenObject
import com.example.bookstorecompose.ui.login.data.MainScreenDataObject
import com.example.bookstorecompose.ui.main_screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            /**
             * если мы хотим открыть другой экран и передать данные, то используем DATA class,
             * если просто открыть экран, то используем объект.
             */
            NavHost(
                navController = navController,
                startDestination = AddScreenObject
            ) {
                /**
                 * Для запуска Логина экрана с сохранением UID пользователя и отправки его данных, при входе
                 */
                composable<LoginScreenObject> {
                    LoginScreen { navData ->
                        navController.navigate(navData)  //если успешно, зарегестрировались, то запускается ф.я navController.navigate(navData) и открывается MainScreen далее
                    }
                }

                /**
                 * Для запуска главного экрана с сохранением UID пользователя и отправки его данных , мпри входе
                 */
                composable<MainScreenDataObject> { navEntry ->
                    val navData =
                        navEntry.toRoute<MainScreenDataObject>()  // возвращает объект данные пользователся
                    MainScreen(navData)
                }
                composable<AddScreenObject> { navEntry ->   //запуск экрана добавления книг
                    AddBookScreen()
                }
            }
        }
    }
}

