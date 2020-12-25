package advent.of.code

import org.junit.Test
import java.math.BigInteger

class Day23Test {

    @Test
    fun `part 1`() {
        val puzzleInput = "538914762"
        val cups = puzzleInput.split("")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .toMutableList()
        val min = cups.minOrNull() ?: -1
        val numberOfCups = cups.size
        var currentCup = cups.first()
        var move = 0
        while (move < 100) {
            move++
            val currentCupIndex = cups.indexOf(currentCup)
            val indexNextToCurrentCup = currentCupIndex + 1
            val pickUp = if (currentCupIndex >= numberOfCups - 4) {
                val lastIndexWrapped = currentCupIndex - numberOfCups + 3
                val result = mutableListOf<Int>()
                if (currentCupIndex < numberOfCups) {
                    result.addAll(cups.slice(currentCupIndex + 1 until numberOfCups))
                }
                result.addAll(cups.slice(0..lastIndexWrapped).toMutableList())
                result

            } else {
                cups.slice(indexNextToCurrentCup..currentCupIndex + 3)
            }
            cups.removeAll(pickUp)
            var destination = currentCup - 1
            while (destination >= min) {
                if (cups.indexOf(destination) >= 0) {
                    break
                }
                destination--
            }
            if (destination < min) {
                destination = cups.maxOrNull() ?: -1
            }
            val indexOfDestination = cups.indexOf(destination)
            cups.addAll(indexOfDestination + 1, pickUp)
            currentCup = cups[(cups.indexOf(currentCup) + 1).rem(cups.size)]
        }

        val indexOfOne = cups.indexOf(1)
        val result = cups.slice(minOf(numberOfCups - 1, indexOfOne + 1) until numberOfCups).toMutableList()
        result.addAll(cups.slice(0 until indexOfOne))
        println(result.joinToString(""))
    }

    @Test
    fun `part 2 - Lost in translation`() {
        val puzzleInput = "538914762"
        val startingCups = puzzleInput.split("")
            .filter { it.isNotEmpty() }
            .map { it.toLong() }
            .toMutableList()
        val min = startingCups.minOrNull() ?: -1
        val max = startingCups.maxOrNull() ?: -1
        for (i in max + 1..1000000) {
            startingCups.add(i)
        }
        val cups = startingCups.mapIndexed { index, value ->
            Pair(value, startingCups[(index + 1).rem(startingCups.size)])
        }.toMap().toMutableMap()
        var currentCup = startingCups.first()
        var move = 0
        val pickUpSize = 3
        while (move < 10000000) {
            move++
            var nextCup = cups[currentCup]!!
            val pickUp = mutableListOf(nextCup)
            for (i in 1 until pickUpSize) {
                nextCup = cups[nextCup]!!
                pickUp.add(nextCup)
            }
            var destination = currentCup - 1
            while (destination >= min) {
                if (!pickUp.contains(destination)) {
                    break
                }
                destination--
            }
            if (destination < min) {
                destination = cups.filter { !pickUp.contains(it.value) }
                    .maxByOrNull { it.value }!!
                    .value
            }
            val nextToDestination = cups[destination]!!
            cups[destination] = pickUp.first()
            cups[currentCup] = cups[pickUp.last()]!!
            cups[pickUp.last()] = nextToDestination
            currentCup = cups[currentCup]!!
        }
        val nextToOne = cups[1]!!
        val nextToNextToOne = cups[nextToOne]!!
        println(nextToOne * nextToNextToOne)
    }
}