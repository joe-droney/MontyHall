package com.jdroney2.monty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdroney2.monty.components.GameState

@Composable
fun Sidebar(state: GameState) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(100.dp)
            .padding(start = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Wins:",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${state.wins}",
            color = Color(0xFF4CAF50),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Losses:",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            text = "${state.losses}",
            color = Color(0xFFE53935),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}