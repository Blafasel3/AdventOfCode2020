package org.example

import org.junit.Test

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
    fun `part 2`() {
        val testInput = "7,13,x,x,59,x,31,19"
        val gaps = listOf(1L, 3L, 2L, 1L, 1L)

        val split = testInput.split(",")

        val busIds = split.filter { it != "x" }.map(String::toLong).withIndex()
        val gapsV2 = Array(busIds.count()) { 1L}

        var denominator = 1L
        whileLoop@ while (true) {
            var previousNumber = denominator.times(7)
            for (busId in busIds) {
                val index = busId.index
                if (index == 0) {
                    continue
                }
                val reminder = (previousNumber + gaps[index - 1]).rem(busId.value)
                if (reminder > 0) {
                    denominator++
                    continue@whileLoop
                }
                previousNumber += reminder + gaps[index - 1]
            }
            println(previousNumber)
            println(previousNumber - testInput.split(",").size + 1)
            break@whileLoop
        }
        println(denominator)
    }

    private val puzzleInput =
        "13,x,x,41,x,x,x,37,x,x,x,x,x,419,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,19,x,x,x,23,x,x,x,x,x,29,x,421,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,17"
}