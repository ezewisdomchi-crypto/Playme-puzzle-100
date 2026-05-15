package com.puzzle.game.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puzzle.game.data.Puzzle
import com.puzzle.game.data.PuzzleGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val generator = PuzzleGenerator()

    private val _currentLevel = MutableStateFlow(0)
    val currentLevel: StateFlow<Int> = _currentLevel

    private val _puzzle = MutableStateFlow<Puzzle?>(null)
    val puzzle: StateFlow<Puzzle?> = _puzzle

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    init { loadPuzzle(0) }

    fun loadPuzzle(level: Int) {
        _currentLevel.value = level
        _puzzle.value = generator.generate(level)
    }

    fun submitAnswer(selected: Int) {
        val correct = _puzzle.value?.correctAnswer == selected
        if(correct) {
            _score.value += 10
            if(_currentLevel.value < 100) loadPuzzle(_currentLevel.value + 1)
        } else {
            _score.value = maxOf(0, _score.value - 5)
        }
    }
}
