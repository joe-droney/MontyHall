package com.jdroney2.monty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jdroney2.monty.ui.theme.MontyTheme
import com.jdroney2.monty.MontyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MontyTheme {
                val viewModel: MontyViewModel = viewModel()
                val state by viewModel.gameState.collectAsState()

                MontyApp(state = state, viewModel = viewModel)
            }
        }
    }
}