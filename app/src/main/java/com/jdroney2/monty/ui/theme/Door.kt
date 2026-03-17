package com.jdroney2.monty.ui.theme

data class Door(
    val id: Int,
    val hasPrize: Boolean = false,
    val isRevealed: Boolean = false,
    val isSelected: Boolean = false
)