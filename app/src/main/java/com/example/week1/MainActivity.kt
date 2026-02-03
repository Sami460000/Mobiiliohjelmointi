package com.example.week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week1.ui.theme.Week1Theme
import com.example.week1.view.CalendarScreen
import com.example.week1.view.HomeScreen
import com.example.week1.view.ROUTE_CALENDAR
import com.example.week1.view.ROUTE_HOME
import com.example.week1.view.ROUTE_THIRD
import com.example.week1.view.ThirdScreen
import com.example.week1.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week1Theme {
                AppNav()
            }
        }
    }
}

@Composable
private fun AppNav() {
    val navController = rememberNavController()

    // IMPORTANT: ViewModel created once here -> shared between screens
    val vm: TaskViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = ROUTE_HOME
    ) {
        composable(ROUTE_HOME) {
            HomeScreen(
                vm = vm,
                onOpenCalendar = { navController.navigate(ROUTE_CALENDAR) },
                onOpenThird = { navController.navigate(ROUTE_THIRD)}
            )
        }
        composable(ROUTE_CALENDAR) {
            CalendarScreen(
                vm = vm,
                onBackToHome = { navController.navigate(ROUTE_HOME) },
                onOpenThird = { navController.navigate(ROUTE_THIRD)}
            )
        }
        composable(ROUTE_THIRD) {
            ThirdScreen()
        }

    }
}
