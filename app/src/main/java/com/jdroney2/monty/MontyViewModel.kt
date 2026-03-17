package com.jdroney2.monty

import kotlin.collections.filter
import kotlin.collections.map


import androidx.lifecycle.ViewModel
import com.jdroney2.monty.Door

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MontyViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private var prizeDoorId: Int = -1
    private var firstPickId: Int = -1

    init {
        startNewGame()
    }

    fun startNewGame() {
        prizeDoorId = (0..2).random()
        firstPickId = -1

        val doors = List(3) { index ->
            Door(id = index, hasPrize = index == prizeDoorId)
        }

        _gameState.update {
            it.copy(doors = doors, phase = GamePhase.PICKING)
        }
    }

    fun selectDoor(doorId: Int) {
        if (_gameState.value.phase != GamePhase.PICKING) return
        firstPickId = doorId

        val selected = _gameState.value.doors.map { door ->
            door.copy(isSelected = door.id == doorId)
        }

        val revealed = revealGoatDoor(selected, doorId)

        _gameState.update {
            it.copy(doors = revealed, phase = GamePhase.SWITCHING)
        }
    }

    fun makeDecision(stay: Boolean) {
        if (_gameState.value.phase != GamePhase.SWITCHING) return

        val current = _gameState.value.doors

        val finalDoorId = if (stay) {
            firstPickId
        } else {
            current.first { !it.isSelected && !it.isRevealed }.id
        }

        val didWin = finalDoorId == prizeDoorId

        val finalDoors = current.map { door ->
            door.copy(
                isSelected = door.id == finalDoorId,
                isRevealed = true
            )
        }

        _gameState.update { state ->
            state.copy(
                doors = finalDoors,
                phase = if (didWin) GamePhase.RESULT_WIN else GamePhase.RESULT_LOSE,
                wins = if (didWin) state.wins + 1 else state.wins,
                losses = if (!didWin) state.losses + 1 else state.losses,
                totalGames = state.totalGames + 1,
                balance = if (didWin) state.balance + 50 else state.balance - 50
            )
        }
    }

    fun resetGame() {
        startNewGame()
    }

    private fun revealGoatDoor(doors: List<Door>, selectedId: Int): List<Door> {
        val goat = doors.filter { !it.hasPrize && it.id != selectedId }.random()
        return doors.map { door ->
            if (door.id == goat.id) door.copy(isRevealed = true) else door
        }
    }
}
