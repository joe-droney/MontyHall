package com.jdroney2.monty

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jdroney2.monty.Door
import com.jdroney2.monty.GamePhase
import com.jdroney2.monty.GameState
import com.jdroney2.monty.toDrawable
import com.jdroney2.monty.MontyViewModel

@Composable
fun GameCards(state: GameState, viewModel: MontyViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 3 cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            state.doors.forEach { door ->
                CardSlot(
                    door = door,
                    phase = state.phase,
                    onClick = {
                        when (state.phase) {
                            GamePhase.PICKING -> viewModel.selectDoor(door.id)
                            GamePhase.SWITCHING -> {
                                if (!door.isRevealed) {
                                    viewModel.makeDecision(stay = door.isSelected)
                                }
                            }
                            else -> {}
                        }
                    }
                )
            }
        }

        // Winner overlay
        if (state.phase == GamePhase.RESULT_WIN) {
            Image(
                painter = painterResource(id = R.drawable.winner),
                contentDescription = "Winner",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(3f),
                contentScale = ContentScale.Fit
            )
        }

        // Loser overlay
        if (state.phase == GamePhase.RESULT_LOSE) {
            Image(
                painter = painterResource(id = R.drawable.loser),
                contentDescription = "Loser",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(3f),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun CardSlot(door: Door, phase: GamePhase, onClick: () -> Unit) {
    val borderColor = when {
        door.isSelected && !door.isRevealed -> Color(0xFFFFD700) // gold border on pick
        else -> Color.Transparent
    }

    val alpha = when {
        door.isRevealed && !door.hasPrize -> 0.5f // dim revealed goat
        else -> 1f
    }

    val clickable = when (phase) {
        GamePhase.PICKING -> true
        GamePhase.SWITCHING -> !door.isRevealed
        else -> false
    }

    Box(
        modifier = Modifier
            .width(120.dp)
            .aspectRatio(0.65f)
            .alpha(alpha)
            .border(3.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(enabled = clickable) { onClick() }
    ) {
        Image(
            painter = painterResource(id = door.toDrawable()),
            contentDescription = "Door ${door.id}",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}