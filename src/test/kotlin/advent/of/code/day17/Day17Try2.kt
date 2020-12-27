package advent.of.code.day17

import org.junit.Test
import kotlin.test.assertEquals

class Day17Try2 {

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
        val testInput = """
            .#.
            ..#
            ###
        """.trimIndent()
        val resultTestInput = getActiveNodes(testInput)
        assertEquals(112, resultTestInput)
//        val resultPuzzleInput = getActiveNodes(puzzleInput)
//        assertEquals(306, resultPuzzleInput)
    }

    private fun getActiveNodes(input: String, bootCycleLength: Int = 6): Int {
        val rows = input.lines()
        val cubes = getInitialCubes(rows, bootCycleLength)
        for (z in 1..bootCycleLength) {
            // TODO missing z = -1 plane for z = 0
            for (cube in cubes) {
                val numberOfActiveNeighbours = cubes.asSequence()
                    .filter { it.isActive }
                    .filter { it.isDirectNeighbour(cube) }
                    .count()
                if (numberOfActiveNeighbours !in 2..3) {
                    cube.isActive = false
                } else if (numberOfActiveNeighbours == 3) {
                    cube.isActive = true
                }
            }
        }
        return cubes.filter { it.isActive }.count()
    }

    private fun getInitialCubes(rows: List<String>, bootCycleLength: Int): MutableList<ConwayCube> {
        val cubes = mutableListOf<ConwayCube>()
        val maxXDimension = rows[0].length + 2 * bootCycleLength
        val maxYDimension = rows.size + 2 * bootCycleLength
        for (z in 0 until bootCycleLength) {
            for (y in 0 until maxYDimension) {
                for (x in 0 until maxXDimension) {
                    val coordinates = ThreeDimensionalCoordinate(x, y, z)
                    val cube = ConwayCube(coordinates)
                    cubes.add(cube)
                }
            }
        }
        val initialPlaneCubes = cubes.filter { it.coordinates.z == 0 }
        for (rowIndex in rows.indices) {
            for (columnIndex in rows[0].indices) {
                initialPlaneCubes.first {
                    bootCycleLength + rowIndex == it.coordinates.y
                            && bootCycleLength + columnIndex == it.coordinates.x
                }.isActive =
                    rows[rowIndex][columnIndex] == '#'
            }
        }
        return cubes
    }
}