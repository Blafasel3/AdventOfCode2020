package advent.of.code.day13

import org.junit.Test
import java.math.BigInteger

class Day13 {

    @Test
    fun `part 1`() {
        val earliestTime = 1002394
        val busIds = puzzleInput
            .split(",")
            .filter { it != "x" }
            .map(String::toInt)
        val idByTime = busIds.map { Pair(it, it - earliestTime.rem(it)) }
            .minByOrNull { it.second }!!
        println(idByTime.first * idByTime.second)
    }

    @Test
    fun `part 2 - Chinese Remainder Theorem with Euclid Factoring`() {
        val input = puzzleInput
        val split = input.split(",")
        val remainders = split.mapIndexed { index, s ->
            if (s.toIntOrNull() != null) {
                index.toBigInteger()
            } else {
                (-1).toBigInteger()
            }
        }.filter { it >= BigInteger.ZERO }
        val primeValues = split.filter { it != "x" }.map(String::toBigInteger)
        val (p, factors) = euclidianFactors(primeValues)
        var sum = factors
            .mapIndexed { index, l -> l * p.div(primeValues[index]) }
            .mapIndexed { index, l -> l * remainders[index] }
            .reduce(BigInteger::add)
        while (sum >= BigInteger.ZERO) { // the found number is the first solution matching sum - k*p for k in N
            sum -= p
        }
        println(sum)
    }

    private fun euclidianFactors(primeValues: List<BigInteger>): Pair<BigInteger, List<BigInteger>> {
        val p = primeValues.reduce { acc, l -> acc.times(l) } // total product of all prime values
        val factors = primeValues.map { primeValue ->
            var r = Pair(p.div(primeValue), primeValue)
            var s = Pair(BigInteger.ONE, BigInteger.ZERO)
            var t = Pair(BigInteger.ZERO, BigInteger.ONE)
            while (r.second != BigInteger.ONE) {
                val q = r.first.div(r.second)
                r = Pair(r.second, r.first - q * r.second)
                s = Pair(s.second, s.first - q * s.second)
                t = Pair(t.second, t.first - q * t.second)
            }
            s.second
        }
        return Pair(p, factors)
    }

    private val puzzleInput =
        "13,x,x,41,x,x,x,37,x,x,x,x,x,419,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,19,x,x,x,23,x,x,x,x,x,29,x,421,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,17"
}