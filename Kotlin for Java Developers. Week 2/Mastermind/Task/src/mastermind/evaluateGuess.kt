package mastermind

import java.lang.Math.min

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    return Evaluation(
            guess.asSequence().zip(secret.asSequence()).count { it.first == it.second },
            getCharFrequency(guess, secret).map { it.key to min(it.value, getCharFrequency(secret, guess).getOrDefault(it.key, 0)) }.toMap().values.sum())
}

fun getCharFrequency(first: String, second: String): Map<Char, Int> {
    val diffs = first.asSequence().withIndex().filter { x -> second.withIndex().none { it == x } }
    return diffs.asSequence().groupBy { it.value }.mapValues { it.value.size }
}
