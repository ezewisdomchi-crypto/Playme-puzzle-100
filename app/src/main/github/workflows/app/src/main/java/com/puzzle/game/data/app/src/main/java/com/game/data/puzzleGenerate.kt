package com.puzzle.game.data
import kotlin.random.Random

class PuzzleGenerator {
    fun generate(level: Int): Puzzle {
        val rand = Random(level + 1000)
        return when {
            level <= 20 -> generateArithmetic(rand, level)
            level <= 40 -> generateSquares(rand, level)
            level <= 60 -> generateFibonacci(rand, level)
            level <= 80 -> generatePrimes(rand, level)
            else -> generateHard(rand, level)
        }
    }

    private fun generateArithmetic(rand: Random, level: Int): Puzzle {
        val start = rand.nextInt(1, 10)
        val step = rand.nextInt(1, 5) + level / 10
        val seq = List(4) { start + it * step }
        val answer = seq[3]
        val question = seq.mapIndexed { i, n -> if(i == 3) "?" else n.toString() }.joinToString(", ")
        return Puzzle(level, question, generateOptions(answer, rand), answer)
    }

    private fun generateSquares(rand: Random, level: Int): Puzzle {
        val start = 1 + level / 30
        val seq = List(4) { (start + it) * (start + it) }
        val answer = seq[3]
        val question = seq.mapIndexed { i, n -> if(i == 3) "?" else n.toString() }.joinToString(", ")
        return Puzzle(level, question, generateOptions(answer, rand), answer)
    }

    private fun generateFibonacci(rand: Random, level: Int): Puzzle {
        val seq = mutableListOf(1, 1)
        repeat(3) { seq.add(seq[seq.size-1] + seq[seq.size-2]) }
        val answer = seq[4]
        val question = seq.mapIndexed { i, n -> if(i == 4) "?" else n.toString() }.joinToString(", ")
        return Puzzle(level, question, generateOptions(answer, rand), answer)
    }

    private fun generatePrimes(rand: Random, level: Int): Puzzle {
        val primes = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
        val seq = primes.drop(level % 3).take(4)
        val answer = seq[3]
        val question = seq.mapIndexed { i, n -> if(i == 3) "?" else n.toString() }.joinToString(", ")
        return Puzzle(level, question, generateOptions(answer, rand), answer)
    }

    private fun generateHard(rand: Random, level: Int): Puzzle {
        val base = 2 + level / 50
        val seq = List(4) { Math.pow(base.toDouble(), (it+1).toDouble()).toInt() }
        val answer = seq[3]
        val question = seq.mapIndexed { i, n -> if(i == 3) "?" else n.toString() }.joinToString(", ")
        return Puzzle(level, question, generateOptions(answer, rand), answer)
    }

    private fun generateOptions(correct: Int, rand: Random): List<Int> {
        val options = mutableSetOf(correct)
        while(options.size < 4) {
            options.add(correct + rand.nextInt(-10, 11))
        }
        return options.shuffled()
    }
}
