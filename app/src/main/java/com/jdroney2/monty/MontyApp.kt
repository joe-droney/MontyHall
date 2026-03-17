package com.jdroney2.monty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdroney2.monty.GameState
import com.jdroney2.monty.MontyViewModel

@Composable
fun MontyApp(state: GameState, viewModel: MontyViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B5E20)) // dark green felt
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            // Left — Sidebar
            Sidebar(state = state)

            // Center/Right — Cards + Bottom bar
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // Cards area (takes up most of the screen)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    GameCards(state = state, viewModel = viewModel)
                }

                // Bottom bar — balance + buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "You currently have: $${state.balance}",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    GameButtons(state = state, viewModel = viewModel)
                }
            }
        }
    }
}