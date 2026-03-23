package com.jdroney2.monty.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jdroney2.monty.Door
import com.jdroney2.monty.MontyViewModel
import com.jdroney2.monty.R
import com.jdroney2.monty.toDrawable
import kotlinx.coroutines.delay

@Composable
fun GameCards(state: GameState, viewModel: MontyViewModel) {

    var spread by remember { mutableStateOf(false) }

    LaunchedEffect(state.phase) {
        when (state.phase) {
            GamePhase.IDLE -> {
                spread = false
            }
            GamePhase.PICKING -> {
                spread = false
                delay(300)
                spread = true
            }
            else -> {}
        }
    }

    val spreadOffsets = listOf(-160f, 0f, 160f)
    val stackRotations = listOf(-6f, 0f, 6f)
    val animSpec = tween<Float>(durationMillis = 500)

    val offsets = spreadOffsets.map { target ->
        animateFloatAsState(
            targetValue = if (spread) target else 0f,
            animationSpec = animSpec,
            label = "offset"
        ).value
    }

    val rotations = stackRotations.map { target ->
        animateFloatAsState(
            targetValue = if (spread) 0f else target,
            animationSpec = animSpec,
            label = "rotation"
        ).value
    }

    val isWin  = state.phase == GamePhase.RESULT_WIN
    val isLose = state.phase == GamePhase.RESULT_LOSE

    val winAlpha by animateFloatAsState(
        targetValue = if (isWin) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "winAlpha"
    )

    val loseAlpha by animateFloatAsState(
        targetValue = if (isLose) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "loseAlpha"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        state.doors.indices.reversed().forEach { index ->
            val door = state.doors[index]
            CardSlot(
                door = door,
                phase = state.phase,
                offsetX = offsets[index],
                rotation = rotations[index],
                onClick = {
                    if (state.phase == GamePhase.PICKING) {
                        viewModel.selectDoor(door.id)
                    }
                }
            )
        }

        if (winAlpha > 0f) {
            Image(
                painter = painterResource(id = R.drawable.winner),
                contentDescription = "Winner",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(3f)
                    .alpha(winAlpha),
                contentScale = ContentScale.Fit
            )
        }

        if (loseAlpha > 0f) {
            Image(
                painter = painterResource(id = R.drawable.loser),
                contentDescription = "Loser",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(3f)
                    .alpha(loseAlpha),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun CardSlot(
    door: Door,
    phase: GamePhase,
    offsetX: Float,
    rotation: Float,
    onClick: () -> Unit
) {
    val isResult = phase == GamePhase.RESULT_WIN || phase == GamePhase.RESULT_LOSE


    val shouldFlip = door.isSelected && (phase == GamePhase.SWITCHING || isResult)

    val flipAngle by animateFloatAsState(
        targetValue = if (shouldFlip) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "flip"
    )


    val isFaceUp = flipAngle > 90f

    val borderColor = when {
        door.isSelected && isResult -> Color(0xFFFFD700)
        else -> Color.Transparent
    }

    val cardAlpha = when {
        door.isRevealed && !door.hasPrize && isResult -> 0.5f
        else -> 1f
    }

    val clickable = phase == GamePhase.PICKING

    Box(
        modifier = Modifier
            .width(120.dp)
            .aspectRatio(0.65f)
            .offset(x = offsetX.dp)
            .rotate(rotation)
            .alpha(cardAlpha)
            .border(3.dp, borderColor, RoundedCornerShape(8.dp))
            .graphicsLayer {

                rotationY = flipAngle
                cameraDistance = 12f * density
            }
            .clickable(enabled = clickable) { onClick() }
    ) {
        Image(
            painter = painterResource(
                // Swap image at the halfway point of the flip
                id = if (isFaceUp) door.toDrawable() else R.drawable.card_back
            ),
            contentDescription = "Door ${door.id}",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {

                    rotationY = if (isFaceUp) 180f else 0f
                }
        )
    }
}