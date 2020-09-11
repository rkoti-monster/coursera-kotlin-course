package mastermind

import java.lang.Math.min

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    return Evaluation(
            guess.zip(secret).count { it.first == it.second },
            getCharFrequency(guess, secret).map { it.key to min(it.value, getCharFrequency(secret, guess).getOrDefault(it.key, 0)) }.toMap().values.sum())
}

fun getCharFrequency(first: String, second: String): Map<Char, Int> {
    val diffs = first.withIndex().filter { x -> second.withIndex().none { it == x } }
    return diffs.groupBy { x -> x.value }.mapValues { it.value.size }
}
