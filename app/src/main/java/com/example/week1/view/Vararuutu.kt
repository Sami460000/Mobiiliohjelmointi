package com.example.week1.view

import android.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.fromColorLong
import androidx.compose.ui.unit.dp



@Composable
fun ThirdScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        Text("Lisää tulossa pian")
        Spacer(Modifier.height(8.dp))
        Text("Ei toimi vielä")
    }
}


