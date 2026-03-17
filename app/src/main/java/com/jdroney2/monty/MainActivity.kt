package com.jdroney2.monty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jdroney2.monty.ui.theme.MontyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MontyTheme {
                val viewModel: MontyViewModel = viewModel()
                val state by viewModel.gameState.collectAsState()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Phase: ${state.phase}")
                    Text("Prize door: hidden")
                    Text("Wins: ${state.wins}  Losses: ${state.losses}")

                    Button(onClick = { viewModel.selectDoor(0) }) { Text("Pick Door 0") }
                    Button(onClick = { viewModel.selectDoor(1) }) { Text("Pick Door 1") }
                    Button(onClick = { viewModel.selectDoor(2) }) { Text("Pick Door 2") }

                    Button(onClick = { viewModel.makeDecision(stay = true) })  { Text("Stay") }
                    Button(onClick = { viewModel.makeDecision(stay = false) }) { Text("Switch") }

                    Button(onClick = { viewModel.startNewGame() }) { Text("New Game") }

                    state.doors.forEach { door ->
                        Text("Door ${door.id} — selected: ${door.isSelected}  revealed: ${door.isRevealed}  prize: ${door.hasPrize}")
                    }
                }
            }
        }
    }
}
