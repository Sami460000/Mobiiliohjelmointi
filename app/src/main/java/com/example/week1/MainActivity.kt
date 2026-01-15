package com.example.week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.week1.ui.HomeScreen
import com.example.week1.ui.theme.Week1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week1Theme {
                HomeScreen()
            }
        }
    }
}
