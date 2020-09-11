package rationals

import java.math.BigInteger

class Rational(n: BigInteger, d: BigInteger): Comparable<Rational> {
    private val numerator: BigInteger = run {
        val negate = n.signum() != d.signum()
        val result = n.divide(n.abs().gcd(d.abs()))
        if (negate) {
            result.abs().negate()
        } else {
            result.abs()
        }
    }

    private val denominator: BigInteger = run {
        d.divide(n.abs().gcd(d.abs())).abs()
    }

    operator fun plus(other: Rational): Rational {
        val denominator = this.denominator.multiply(other.denominator)
        val numerator = this.numerator.multiply(other.denominator).plus(other.numerator.multiply(this.denominator))
        return Rational(numerator, denominator)
    }

    operator fun minus(other: Rational): Rational {
       return this.plus(-other)
    }

    operator fun times(other: Rational): Rational {
        val denominator = this.denominator.multiply(other.denominator)
        val numerator = this.numerator.multiply(other.numerator)
        return Rational(numerator, denominator)
    }

    private fun flip(): Rational {
        return Rational(this.denominator, this.numerator)
    }

    operator fun div(other: Rational): Rational {
        return this * other.flip()
    }

    operator fun unaryMinus(): Rational {
        return Rational(this.numerator.negate(), this.denominator)
    }

    override operator fun compareTo(other: Rational): Int {
        val left = this.numerator * other.denominator
        val right = other.numerator * this.denominator
        return left.compareTo(right)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other is Rational) {
            return this.numerator == other.numerator && this.denominator == other.denominator
        }
        return false
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }

    override fun toString(): String {
        val d = if(denominator == BigInteger.ONE) "" else "/$denominator"
        return "$numerator$d"
    }
}


infix fun Int.divBy(denominator: Int): Rational {
    val n = BigInteger.valueOf(this.toLong())
    val d = BigInteger.valueOf(denominator.toLong())
    val gcd = n.gcd(d)
    return Rational(n.divide(gcd), d.divide(gcd))
}

infix fun Long.divBy(denominator: Long): Rational {
    val n = BigInteger.valueOf(this)
    val d = BigInteger.valueOf(denominator)
    val gcd = n.gcd(d)
    return Rational(n.divide(gcd), d.divide(gcd))
}

infix fun BigInteger.divBy(denominator: BigInteger): Rational {
    val gcd = this.gcd(denominator)
    return Rational(this.divide(gcd), denominator.divide(gcd))
}

fun String.toRational(): Rational {
    val parts = this.split("/")
    println(parts)
    return Rational(parts[0].toBigInteger(), if(parts.size == 1) BigInteger.ONE else parts[1].toBigInteger())
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)

    val m = HashMap<Int, Int>();
    m.getOrPut
}