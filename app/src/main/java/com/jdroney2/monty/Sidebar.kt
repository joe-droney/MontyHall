package com.jdroney2.monty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF33130D))
            .padding(16.dp),
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
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Losses:",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "${state.losses}",
            color = Color(0xFFE53935),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Balance:",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "$${state.balance}",
            color = Color(0xFFFFD700), // gold
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}