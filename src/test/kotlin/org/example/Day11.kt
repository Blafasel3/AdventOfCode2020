package org.example

import org.junit.Ignore
import org.junit.Test

class Day11 {

    @Test
    @Ignore
    fun `part 1 adjacent seating system`() {
        val testInput = puzzleInput
        val (floorCoordinates, coordinateMatrix) = getInputAsMatrix(testInput)
        val markedForRemoval = mutableListOf<Pair<Int, Int>>()
        val markedForSeating = mutableListOf<Pair<Int, Int>>()
        var numberOfRuns = 0
        val rowDimension = coordinateMatrix.size
        val columnDimension = coordinateMatrix[0].size
        while (true) {
            numberOfRuns++
            for (i in coordinateMatrix.indices) {
                for (j in coordinateMatrix[i].indices) {
                    if (floorCoordinates.contains(Pair(i, j))) {
                        continue // no need to check
                    }

                    // lookup adjacent seats
                    val numberOfOccupiedSeats = coordinateMatrix
                        .slice(maxOf(0, i - 1)..minOf(i + 1, rowDimension - 1))
                        .flatMap { it.slice(maxOf(0, j - 1)..minOf(j + 1, columnDimension - 1)) }
                        .filter { it == '#' }
                        .count() - 1 // if current seat is '#' we have to subtract minus one, we check in L later
                    if (numberOfOccupiedSeats >= 4 && coordinateMatrix[i][j] == '#') {
                        markedForRemoval.add(Pair(i, j))
                    } else if (
                        numberOfOccupiedSeats < 0
                        && coordinateMatrix[i][j] == 'L'
                    ) {
                        markedForSeating.add(Pair(i, j))
                    }
                }
            }

            if (markedForRemoval.isEmpty() && markedForSeating.isEmpty()) {
                val numberOfOccupiedSeats = coordinateMatrix
                    .flatMap { it.asIterable() }
                    .filter { it == '#' }
                    .count()
                println("Number of occupied Seats: $numberOfOccupiedSeats")
                println("Took ${numberOfRuns - 1} rounds to stabilize")
                break
            }
            markedForRemoval.forEach {
                coordinateMatrix[it.first][it.second] = 'L'
            }
            markedForSeating.forEach {
                coordinateMatrix[it.first][it.second] = '#'
            }
            markedForSeating.clear()
            markedForRemoval.clear()
        }
    }

    @Test
    fun `part 2 visibility seating system`() {
        val testInput = puzzleInput
        val (floorCoordinates, coordinateMatrix) = getInputAsMatrix(testInput)
        val markedForRemoval = mutableListOf<Pair<Int, Int>>()
        val markedForSeating = mutableListOf<Pair<Int, Int>>()
        var numberOfRuns = 0
        val numberOfOccupiedSeats = findVisibleOccupiedSeats(coordinateMatrix, 1, 0)
        println(numberOfOccupiedSeats)

        while (true) {
            numberOfRuns++
            for (rowIndex in coordinateMatrix.indices) {
                for (columnIndex in coordinateMatrix[rowIndex].indices) {
                    if (floorCoordinates.contains(Pair(rowIndex, columnIndex))) {
                        continue // no need to check, floor never changes
                    }

                    val numberOfOccupiedSeats = findVisibleOccupiedSeats(coordinateMatrix, rowIndex, columnIndex)
                    if (numberOfOccupiedSeats >= 5 && coordinateMatrix[rowIndex][columnIndex] == '#') {
                        markedForRemoval.add(Pair(rowIndex, columnIndex))
                    } else if (
                        numberOfOccupiedSeats == 0
                        && coordinateMatrix[rowIndex][columnIndex] == 'L'
                    ) {
                        markedForSeating.add(Pair(rowIndex, columnIndex))
                    }
                }
            }

            if (markedForRemoval.isEmpty() && markedForSeating.isEmpty()) {
                val numberOfOccupiedSeats = coordinateMatrix
                    .flatMap { it.asIterable() }
                    .filter { it == '#' }
                    .count()
                println("Number of occupied Seats: $numberOfOccupiedSeats")
                println("Took ${numberOfRuns } rounds to stabilize")
                break
            }
            markedForRemoval.forEach {
                coordinateMatrix[it.first][it.second] = 'L'
            }
            markedForSeating.forEach {
                coordinateMatrix[it.first][it.second] = '#'
            }
            markedForSeating.clear()
            markedForRemoval.clear()
            println("-------------------- Run $numberOfRuns")
//            printMatrix(coordinateMatrix)
        }
    }

    private val directions = listOf(
        Pair(-1, -1),
        Pair(-1, 0),
        Pair(-1, 1),
        Pair(0, 1),
        Pair(1, 1),
        Pair(1, 0),
        Pair(1, -1),
        Pair(0, -1)
    )

    private fun findVisibleOccupiedSeats(
        coordinateMatrix: Array<Array<Char>>,
        rowPosition: Int,
        colPosition: Int
    ): Int {
        val rowDimension = coordinateMatrix.size
        val columnDimension = coordinateMatrix[0].size
        var numberOfOccupiedSeats = 0
        val columnRange = 0 until columnDimension
        val rowRange = 0 until rowDimension
        directionLoop@ for (direction in directions) {
            for (i in 1..maxOf(rowDimension, columnDimension)) {
                val row = rowPosition + direction.first * i
                val column = colPosition + direction.second * i
                if (row !in rowRange || column !in columnRange) {
                    continue@directionLoop
                } else if (coordinateMatrix[row][column] == '#') {
                    numberOfOccupiedSeats++ // found occupied seat, can stop
                    continue@directionLoop
                } else if (coordinateMatrix[row][column] == 'L') {
                    continue@directionLoop // found unoccupied seat, can stop
                }
            }
        }
        return numberOfOccupiedSeats
    }

    private fun getInputAsMatrix(input: String): Pair<MutableList<Pair<Int, Int>>, Array<Array<Char>>> {
        val rows = input.lines()
        val floorCoordinates = mutableListOf<Pair<Int, Int>>()
        val numberOfColumns = rows[0].length
        val coordinateMatrix = Array(rows.size) { Array(numberOfColumns) { '.' } }
        for (i in coordinateMatrix.indices) {
            for (j in coordinateMatrix[i].indices) {
                when {
                    rows[i][j] == '.' -> floorCoordinates.add(Pair(i, j)) // this is floor, never changes
                    rows[i][j] == 'L' -> coordinateMatrix[i][j] = '#'
                    else -> coordinateMatrix[i][j] = rows[i][j]
                }
            }
        }
        return Pair(floorCoordinates, coordinateMatrix)
    }

    private fun printMatrix(coordinateMatrix: Array<Array<Char>>) {
        for (element in coordinateMatrix) {
            for (j in element) {
                print(j)
                print(" ")
            }
            println()
        }
    }

    private val puzzleInput = """
        LLLLLLLLL.L.LLLLLLLLLLLLL.LLLLLLL.LLLL.LLLLLLLLLLLLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LL.LLLLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLLL
        LLLLLLLLLL.LLLLL.LLLLLLLL.LLLLLLL.LLLL.LLLLLLLLLLLL.LLLLLLLLLL.LLLLL.LLLLLLL.LLLLL.LLLLLL.LLLLLLLL
        LLLLLLLLL.LLLLLL..LLLLLLL.LLLLLLL.LLLL.LLLLLLLLLLLLLLLLLL.LLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLLLL
        LLLLLLL.L.LLLLLLLLLLLLLLLLLLLLLLL.LLLL..LLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLL.LLLLLLLLLLLLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        L.LLLL.L..L.LL....L......L...LL.LL..L....L...LL.LL.L.LL.LL..LL............L..L...LL.L.L.L..LLL...L
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.L.LLLLLL.LLLLLLLLLL.LLLL.LL.LLLLL.LLLLLLLL.LLLLLL
        LLLLLLLLLLLLLLLL.LLLLLLLL.LLLL.LL.LLLL.LLLLLLLLL.LLL.LLLL..LLL.LLLLL.LL.LLLL.LLLLL.LLLLLLLLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLLLLL..LLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLL..LLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLL
        LLLLLLLLL.LL.LLL.LLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LL.LLLLLL.LLLL.LLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLL..LLLLLLLL..LLL.LLLLL.LL.LLLLLLLLLL.LLLLLLLLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        ....LL.....LL..LLL..L......LL.LL......L.....L.....LL.LLLL..L.L.......LL.LL.....L.........L...L..L.
        LLLLLLL.L.LLLLLL.LLLLLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLLLLLLLL
        LLLLLLLLL.LLLLLL.L.LLLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLLL.LLLLLLLL.LLLLLLLLL
        .LLL....L....LL....L..........LL.L................L...L...LL......L.....LLLL...LL..L.....L.L..L.L.
        LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLL.LLLL.LLL.LLLLL.LLLLLLLLLLLLL.LLLLL.LL.LLLLLLLLLL.LLLL..LLLLLLLLL
        LLLLLLLLL..LLLLLLLLLL.LLL.LLLLLLLLLLLL.LLLLLLLLL.LLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLL.LLLLL.LLL..LLLL
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLL
        LLLLLLLLLLLLLL.L.LLLLLLLL.LLLLLLL.LLLLLLLLLLLLLL.L.LLLLLL.LLLLLLLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLLLLLLLL.LLLLLLLLL
        .L.LL..L.....L...L...L.L...L..L..L.L.L.L........L..LL....L...L...L.....LL.L............L..L..L....
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLLLLLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLL.LLLLLLLLLLLLLL.LLLLL.LL.LLLL.LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.L.
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLL.L.LLLL.LLLLLLLLL.LLLLLLLL.L.LLLLLLLLLLLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLL.L.LLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLL.LLLLLLLLLLLLLLLL.LL.LLLL..LLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLL.L.LLLLL.LLLLLLLLL
        L.L..L.L..L.....LL...LL..LL..L.L...L...L.....L...L.......L...LL.............LL.LL.LL....L.L.LL.L..
        LLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLL.LL.LLLLL.LLL.LLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLLLLLLLL.L.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLL.LLL.LLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        .........L.L.L.LL.LLLLL..LL..LL......LL.L.L.L...L.L.....L.LL.....LLL.L.L.L.....LL...L..L....L..L.L
        LLLLLLLLL.LLLLLL.LLLLLLLL.L.LLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLLL..LLLLLL.LL.LL.LLLL.LLLLL.LLLL
        LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLL.LLLLL.LLL.LLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLL.LLLLL.LLLL.LLLL
        .L.........LL....L..LL.LLL.LL.L...L..L.....LLLL..........L.LLL...LLLL.L..L.L.LLL...LLL..L.L......L
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLLLLLLL.LL.LLLLLL
        LLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLLLL..LLLLLLLL.LLLLLLLL.LLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLLLL
        LLLLL.LLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLL.LLLLL.LLLLLLLLL
        .LL.L.LL.LLL.LLL.L....L....LL..L.L............L.....L.....L....LLLL..L......L.L...LL...LL.L...L.L.
        LLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLL.LLLLL.L.LLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLL.LLL.LLLLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLL.LLLLLLLL.LLL.LLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLL.
        L.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        .L..L.L....L.........L.L...L..L.L..LL.........L.L....L..LL...L....L.L...LLL.L.LL.LL.L..L...LL....L
        LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLL.LLLLLLLL..LLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLLLLL..LLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLL.LLL.LLLLLLLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLL.LL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.L.LL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLL.LLLLLLL.LLL.L.LLLLL.LL.LLLLLL
        LLLLLL.LL.LLLLLL.LLLLLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLL.LL.LLLLL.LLLL..LLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLL.LLL.LLLLL.LLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLLLLLLLL.LL.LLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLLLL.LLLLLLLLLLL.LLLLL.LLLLLLLLL
        .LL..L....L.....L......LLL......L...LLLL....LLL......L..L......L..LL...LL...L......LL.LL....L..L..
        LLLLLLLLL.LLLLLL.LLLLLLLLLL..L.LL.LLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLL.LLLL...LLLLLLL.LLLLLLLL.LLLL.LLLL.LL.LLLLLLLLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLLLLL.LLLLLLLLL.LLLLL.LLLLLLLLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLL.LLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLL
        ...L.LL..........L....L.L.L....L......LL..........L.L.....LL..LL..L..L..L..L...L..L..L..L.....L...
        LLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLL..LL.L.LLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        LLLLLLL.LLLLLLL..LLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLL.LLLLLLL..LLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLLLL
        LLLLLLLLL.L.LLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLL.LLLL.LLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLL.LL.LL.LLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLL.LL..LLL.LLL.L.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLL.LLL.LLLL.LLLLLLLLLLLLL.LLLL.LLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLL.LLLLL.L.LLLLL.LLLLLLLLLLLLLLL
        ..L..L.LL..L.L..LL...L.L...L....LL..L.....L..L..L..L.....LL.L..L.LL......L.LLLL.LLL.LL.....L.L....
        LLLLLLL.L.LLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLLLLLLLL.LLLLLL.LLLLLLL.LLLL.LLLL.L.LLLLLLLLL
        LLLLLLLLL.LLLLLL.LL.LLLLL.LLLLLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLLLLLLLL.LLLLLLLLL
        LLLLL.LLL.LLLLLLLLLLLLL.L.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLLLLLLL.LLLLLLLLLL
        LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLL.L.LLLL.LLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLLLL
        LLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLL.L.LLLLL.LLLLLLLLLLLLLLL
        LLLLLL.LLLLLLLL..LLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLL.L.LLLL.LLLLLLLLLLL.LLLL.LLLLL.LLLLLLLLLLLL.LLLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLLLLLL.LLLLLLLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        L.LLLLLLL.LLLLLL.L.LLLLLL..LLLLLLLLLL.LLLLLLLLLL.LLLLLLLL.LLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLL.LLLLLLLL.LLLLLLLLLLL.L.LLL.L.LLLLLLLLLLLLLLL
        .LLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LL.LLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLLLLL.LLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLLLLLLLLLL.LLLLLLLL.LLLL.LL.LLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLL.LLLLL.LLLLLLLLL
        LLLLLLLL..LLLLLL.LLL.LLLL.LLLLLLL.LLLL.LLLLLLLLL.LLLL.LLL.LLLL.LLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLL
    """.trimIndent()
}