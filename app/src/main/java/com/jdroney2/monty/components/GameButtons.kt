package com.jdroney2.monty.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdroney2.monty.MontyViewModel

@Composable
fun GameButtons(state: GameState, viewModel: MontyViewModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(end = 16.dp, bottom = 8.dp)
    ) {
        // Reset - always available to start over
        Button(
            onClick = { viewModel.resetGame() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF546E7A)
            )
        ) {
            Text("Reset", fontSize = 14.sp)
        }

        // Play - enables card clicking
        Button(
            onClick = { viewModel.play() },
            enabled = state.phase == GamePhase.IDLE,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B5E20),
                disabledContainerColor = Color(0xFF37474F)
            )
        ) {
            Text("Play", fontSize = 14.sp)
        }
    }
}