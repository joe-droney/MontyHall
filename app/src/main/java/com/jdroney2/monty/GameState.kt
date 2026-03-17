package com.jdroney2.monty

import com.jdroney2.monty.ui.theme.Door


data class GameState(
    val doors: List<Door> = emptyList(),
    val phase: GamePhase = GamePhase.PICKING,
    val wins: Int = 0,
    val losses: Int = 0,
    val totalGames: Int = 0
)

enum class GamePhase {
    PICKING,       // Player picks initial door
    SWITCHING,     // Goat revealed — switch or stay?
    RESULT_WIN,
    RESULT_LOSE
}