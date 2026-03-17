package com.jdroney2.monty




data class GameState(
    val doors: List<Door> = emptyList(),
    val phase: GamePhase = GamePhase.PICKING,
    val wins: Int = 0,
    val losses: Int = 0,
    val totalGames: Int = 0,
    val balance: Int = 750
)

enum class GamePhase {
    PICKING,
    SWITCHING,
    RESULT_WIN,
    RESULT_LOSE
}