package com.jdroney2.monty.ui.theme

import com.jdroney2.monty.R

data class Door(
    val id: Int,
    val hasPrize: Boolean = false,
    val isRevealed: Boolean = false,
    val isSelected: Boolean = false
)

fun Door.toDrawable(): Int = when {
    isRevealed && hasPrize   -> R.drawable.ace
    isRevealed && !hasPrize  -> R.drawable.blank
    isSelected               -> R.drawable.blank
    else                     -> R.drawable.card_back
}