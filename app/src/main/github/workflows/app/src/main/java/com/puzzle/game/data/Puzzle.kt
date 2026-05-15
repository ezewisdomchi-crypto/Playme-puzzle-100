package com.puzzle.game.data

data class Puzzle(
    val level: Int,
    val question: String,
    val options: List<Int>,
    val correctAnswer: Int
)
