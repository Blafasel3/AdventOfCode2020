package org.example

import org.junit.Test

class Day17 {

    private val puzzleInput = """
        ##......
        .##...#.
        .#######
        ..###.##
        .#.###..
        ..#.####
        ##.####.
        ##..#.##
    """.trimIndent()

    @Test
    fun `part 1`() {
        val bootCycleLength = 6
        val coordinateSystem = get3DimensionalCoordinateSystem(bootCycleLength, puzzleInput)
        val markedForDeactivation = mutableListOf<Triple<Int, Int, Int>>()
        val markedForActivation = mutableListOf<Triple<Int, Int, Int>>()
        val zDimension = coordinateSystem.size
        val xDimension = coordinateSystem[0].size
        val yDimension = coordinateSystem[0][0].size
        for (bootCycleStep in 1..bootCycleLength) {
            for (z in coordinateSystem.indices) {
                for (x in coordinateSystem[z].indices) {
                    for (y in coordinateSystem[z][x].indices) {
                        val zRange = if (z == 0) { // cannot go to z = -1 -> mirror z = 1 once
                            val result = mutableListOf<Array<Array<Char>>>()
                            result.addAll(coordinateSystem.slice(0..1))
                            result.addAll(coordinateSystem.slice(1..1))
                            result
                        } else {
                            coordinateSystem.slice(maxOf(0, z - 1)..minOf(z + 1, zDimension - 1))
                        }

                        val numberOfActiveNodesNearby = zRange
                            .flatMap { it.slice(maxOf(0, x - 1)..minOf(x + 1, xDimension - 1)) }
                            .flatMap { it.slice(maxOf(0, y - 1)..minOf(y + 1, yDimension - 1)) }
                            .filter { it == '#' }
                            .count()
                        if (coordinateSystem[z][x][y] == '#' && numberOfActiveNodesNearby !in 3..4) {
                            markedForDeactivation.add(Triple(z, x, y))
                        } else if (numberOfActiveNodesNearby == 3 && coordinateSystem[z][x][y] == '.') {
                            markedForActivation.add(Triple(z, x, y))
                        }
                    }
                }
            }
            markedForDeactivation.forEach {
                coordinateSystem[it.first][it.second][it.third] = '.'
            }
            markedForActivation.forEach {
                coordinateSystem[it.first][it.second][it.third] = '#'
            }

            markedForActivation.clear()
            markedForDeactivation.clear()

        }
        printCoordinateSystem(coordinateSystem)
        val numberOfActiveNodes = coordinateSystem.slice(1 until coordinateSystem.size)
            .flatMap { it.slice(it.indices) }
            .flatMap { it.slice(it.indices) }
            .filter { it == '#' }
            .count() * 2 + coordinateSystem[0]
            .flatMap { it.slice(it.indices) }
            .filter { it == '#' }.count()
        println(numberOfActiveNodes)
    }

    @Test
    fun `part 2 4-dimensional`() {
        val testInput = """
            .#.
            ..#
            ###
        """.trimIndent()
        val bootCycleLength = 6
        val coordinateSystem = get4DimensionalCoordinateSystem(bootCycleLength, testInput)
        val markedForDeactivation = mutableListOf<Quadruple>()
        val markedForActivation = mutableListOf<Quadruple>()
        val wDimension = coordinateSystem.size
        val zDimension = coordinateSystem[0].size
        val xDimension = coordinateSystem[0][0].size
        val yDimension = coordinateSystem[0][0][0].size
        for (bootCycleStep in 1..bootCycleLength) {
            for (w in coordinateSystem.indices) {
                val wRange = if (w == 0) {
                    val result = mutableListOf<Array<Array<Array<Char>>>>()
                    result.addAll(coordinateSystem.slice(0..1))
                    result.addAll(coordinateSystem.slice(1..1))
                    result
                } else {
                    coordinateSystem.slice(maxOf(0, w - 1)..minOf(w + 1, wDimension - 1))
                }
                for (z in coordinateSystem[w].indices) {
                    for (x in coordinateSystem[w][z].indices) {
                        for (y in coordinateSystem[w][z][x].indices) {
                            val zRange = if (z == 0) { // cannot go to z = -1 -> mirror z = 1 once
                                val result = mutableListOf<Array<Array<Char>>>()
                                result.addAll(coordinateSystem[w].slice(0..1))
                                result.addAll(coordinateSystem[w].slice(1..1))
                                wRange.flatMap { result.slice(0..2) }
                            } else {
                                wRange.flatMap {
                                    coordinateSystem[w].slice(
                                        maxOf(0, z - 1)..minOf(
                                            z + 1, zDimension - 1
                                        )
                                    )
                                }
                            }

                            val numberOfActiveNodesNearby = zRange
                                .flatMap { it.slice(maxOf(0, x - 1)..minOf(x + 1, xDimension - 1)) }
                                .flatMap { it.slice(maxOf(0, y - 1)..minOf(y + 1, yDimension - 1)) }
                                .filter { it == '#' }
                                .count()
                            if (coordinateSystem[w][z][x][y] == '#' && numberOfActiveNodesNearby !in 3..4) {
                                markedForDeactivation.add(Quadruple(w, z, x, y))
                            } else if (numberOfActiveNodesNearby == 3 && coordinateSystem[w][z][x][y] == '.') {
                                markedForActivation.add(Quadruple(w, z, x, y))
                            }
                        }
                    }
                }
                markedForDeactivation.forEach {
                    coordinateSystem[it.first][it.second][it.third][it.fourth] = '.'
                }
                markedForActivation.forEach {
                    coordinateSystem[it.first][it.second][it.third][it.fourth] = '#'
                }

                markedForActivation.clear()
                markedForDeactivation.clear()
            }
        }
        val numberOfActiveNodes = coordinateSystem.slice(1 until coordinateSystem.size)
            .flatMap { it.slice(it.indices) }
            .flatMap { it.slice(it.indices) }
            .flatMap { it.slice(it.indices) }
            .filter { it == '#' }
            .count() * 1 + coordinateSystem[0]
            .flatMap { it.slice(it.indices) }
            .flatMap { it.slice(it.indices) }
            .filter { it == '#' }.count()
        println(numberOfActiveNodes)
    }

    private class Quadruple(val first: Int, val second: Int, val third: Int, val fourth: Int) {}

    private fun get4DimensionalCoordinateSystem(bootCycleLength: Int, input: String): Array<Array<Array<Array<Char>>>> {
        val rows = input.lines()
        val numberOfColumns = rows[0].length
        val coordinateSystem = Array(bootCycleLength * 2) {
            Array(bootCycleLength * 2) {
                Array(rows.size + bootCycleLength * 2)
                {
                    Array(numberOfColumns + bootCycleLength * 2)
                    { '.' }
                }
            }
        }
        for (rowIndex in rows.indices) {
            for (columnIndex in rows[0].indices) {
                coordinateSystem[0][0][bootCycleLength + rowIndex][bootCycleLength + columnIndex] =
                    rows[rowIndex][columnIndex]
            }
        }
        return coordinateSystem
    }

    private fun get3DimensionalCoordinateSystem(bootCycleLength: Int, input: String): Array<Array<Array<Char>>> {
        val rows = input.lines()
        val numberOfColumns = rows[0].length
        val coordinateSystem = Array(bootCycleLength * 2) {
            Array(rows.size + bootCycleLength * 2)
            {
                Array(numberOfColumns + bootCycleLength * 2)
                { '.' }
            }
        }

        for (rowIndex in rows.indices) {
            for (columnIndex in rows[0].indices) {
                coordinateSystem[0][bootCycleLength + rowIndex][bootCycleLength + columnIndex] =
                    rows[rowIndex][columnIndex]
            }
        }
        return coordinateSystem
    }


    private fun printCoordinateSystem(
        coordinateSystem: Array<Array<Array<Char>>>,
        maxZIndex: Int = coordinateSystem.size
    ) {
        for (zIndex in 0 until maxZIndex) {
            println("z = $zIndex")
            for (j in coordinateSystem[zIndex]) {
                for (i in j) {
                    print(i)
                    print(" ")
                }
                println()
            }
        }
    }
}