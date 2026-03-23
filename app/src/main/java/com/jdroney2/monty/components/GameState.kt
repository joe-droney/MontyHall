package com.jdroney2.monty.components

import com.jdroney2.monty.Door


data class GameState(
    val doors: List<Door> = emptyList(),
    val phase: GamePhase = GamePhase.IDLE,
    val wins: Int = 0,
    val losses: Int = 0,
    val totalGames: Int = 0,
    val balance: Int = 750
)

enum class GamePhase {
    IDLE,
    PICKING,
    SWITCHING,
    RESULT_WIN,
    RESULT_LOSE
}