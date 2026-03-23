package com.jdroney2.monty

import androidx.lifecycle.ViewModel
import com.jdroney2.monty.components.GamePhase
import com.jdroney2.monty.components.GameState

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MontyViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private var prizeDoorId: Int = -1

    init {
        startNewGame()
    }

    fun startNewGame() {
        prizeDoorId = (0..2).random()

        val doors = List(3) { index ->
            Door(id = index, hasPrize = index == prizeDoorId)
        }

        _gameState.update {
            it.copy(doors = doors, phase = GamePhase.IDLE)
        }
    }

    fun play() {
        if (_gameState.value.phase == GamePhase.IDLE) {
            _gameState.update { it.copy(phase = GamePhase.PICKING) }
        }
    }

    fun selectDoor(doorId: Int) {
        if (_gameState.value.phase != GamePhase.PICKING) return

        val didWin = doorId == prizeDoorId

        val finalDoors = _gameState.value.doors.map { door ->
            if (door.id == doorId) {
                door.copy(isRevealed = true, isSelected = true)
            } else {
                // Reveal all doors at the end
                door.copy(isRevealed = true)
            }
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
}
