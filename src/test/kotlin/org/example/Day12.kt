package org.example

import org.junit.Test
import kotlin.math.abs

class Day12 {
    @Test
    fun `part 1 instructions for ship`() {
        val actions = puzzleInput.lines().map {
            val action = it[0]
            val value = it.substring(1 until it.length).toInt()
            Pair(action, value)
        }

        var currentDirection = Direction.E // The ship starts by facing east

        val directionValues = mutableMapOf(
            Direction.N to 0,
            Direction.E to 0,
            Direction.S to 0,
            Direction.W to 0
        )

        actions.forEach {
            when (it.first) {
                'L' -> currentDirection = currentDirection.turn(Turn.L, it.second.div(90))
                'R' -> currentDirection = currentDirection.turn(Turn.R, it.second.div(90))
                'N' -> move(directionValues, it)
                'E' -> move(directionValues, it)
                'S' -> move(directionValues, it)
                'W' -> move(directionValues, it)
                'F' -> directionValues[currentDirection] = directionValues[currentDirection]!! + it.second
                else -> NotImplementedError("Action ${it.first} not valid!")
            }
        }

        println(directionValues)
        val distanceNorthOrSouth = directionValues[Direction.N]!! - directionValues[Direction.S]!!
        val distanceWestOrEast = directionValues[Direction.W]!! - directionValues[Direction.E]!!
        println("North - South: $distanceNorthOrSouth")
        println("West - East: $distanceWestOrEast")
        println(abs(distanceNorthOrSouth) + abs(distanceWestOrEast))
    }

    @Test
    fun `part 2 instructions for waypoint`() {
        val actions = puzzleInput.lines().map {
            val action = it[0]
            val value = it.substring(1 until it.length).toInt()
            Pair(action, value)
        }

        val shipPosition = mutableMapOf(
            Direction.N to 0,
            Direction.E to 0,
            Direction.S to 0,
            Direction.W to 0
        )

        var waypointPosition = mutableMapOf(
            Direction.N to 1,
            Direction.E to 10,
            Direction.S to 0,
            Direction.W to 0
        )

        actions.forEach { action ->
            when (action.first) {
                'L' -> waypointPosition = waypointPosition.mapKeys { it.key.turn(Turn.L, action.second.div(90)) }
                    .toMutableMap()
                'R' -> waypointPosition = waypointPosition.mapKeys { it.key.turn(Turn.R, action.second.div(90)) }
                    .toMutableMap()
                'N' -> {
                    val sum = waypointPosition[Direction.N]!! - waypointPosition[Direction.S]!! + action.second
                    waypointPosition[Direction.N] = maxOf(sum, 0)
                    waypointPosition[Direction.S] = maxOf(waypointPosition[Direction.S]!! - action.second, 0)
                }
                'E' -> {
                    val sum = waypointPosition[Direction.E]!! - waypointPosition[Direction.W]!! + action.second
                    waypointPosition[Direction.E] = maxOf(sum, 0)
                    waypointPosition[Direction.W] = maxOf(waypointPosition[Direction.W]!! - action.second, 0)
                }
                'S' -> {
                    val sum = waypointPosition[Direction.S]!! - waypointPosition[Direction.N]!! + action.second
                    waypointPosition[Direction.S] = maxOf(sum, 0)
                    waypointPosition[Direction.N] = maxOf(waypointPosition[Direction.N]!! - action.second, 0)
                }
                'W' -> {
                    val sum = waypointPosition[Direction.W]!! - waypointPosition[Direction.E]!! + action.second
                    waypointPosition[Direction.W] = maxOf(sum, 0)
                    waypointPosition[Direction.E] = maxOf(waypointPosition[Direction.E]!! - action.second, 0)
                }
                'F' -> moveTowardsWaypoint(shipPosition, action.second, waypointPosition)

                else -> NotImplementedError("Action ${action.first} not valid!")
            }
        }

        println("shipPosition: $shipPosition")
        println("waypointPosition $waypointPosition")
        val distanceNorthOrSouth = shipPosition[Direction.N]!! - shipPosition[Direction.S]!!
        val distanceWestOrEast = shipPosition[Direction.W]!! - shipPosition[Direction.E]!!
        println("North - South: $distanceNorthOrSouth")
        println("West - East: $distanceWestOrEast")
        println(abs(distanceNorthOrSouth) + abs(distanceWestOrEast))
    }

    private fun moveTowardsWaypoint(
        shipPosition: MutableMap<Direction, Int>,
        value: Int,
        waypointPosition: MutableMap<Direction, Int>
    ) {
        shipPosition[Direction.N] = shipPosition[Direction.N]!! + value * waypointPosition[Direction.N]!!
        shipPosition[Direction.E] = shipPosition[Direction.E]!! + value * waypointPosition[Direction.E]!!
        shipPosition[Direction.S] = shipPosition[Direction.S]!! + value * waypointPosition[Direction.S]!!
        shipPosition[Direction.W] = shipPosition[Direction.W]!! + value * waypointPosition[Direction.W]!!
    }

    enum class Turn(val step: Int) { L(-1), R(1) }

    enum class Direction {
        N, E, S, W;

        fun turn(turn: Turn, times: Int): Direction {
            val mod: (Int, Int) -> Int = { n, d -> ((n % d) + d) % d }
            return values()[mod(values().indexOf(this) + turn.step * times, values().size)]
        }
    }

    private fun move(
        directionValues: MutableMap<Direction, Int>,
        it: Pair<Char, Int>
    ) {
        val direction = Direction.valueOf(it.first.toString())
        directionValues[direction] = directionValues[direction]!! + it.second
    }

    private val puzzleInput = """
        R90
        W4
        L90
        N2
        E5
        N1
        N5
        R90
        E2
        L90
        F11
        L180
        W4
        L90
        S5
        L90
        F66
        W4
        F2
        E1
        S5
        W5
        F86
        E3
        R90
        N2
        F91
        F94
        R180
        W5
        F18
        R180
        F100
        W5
        F40
        W2
        R180
        F2
        E3
        N4
        W1
        L180
        N3
        E3
        S1
        L180
        W3
        R180
        E2
        F84
        L90
        W1
        N1
        F10
        N4
        R90
        E4
        F55
        S1
        W4
        S5
        W2
        R90
        S5
        E5
        F41
        S1
        W5
        F84
        E3
        F48
        F12
        L90
        F79
        N5
        E2
        F34
        E5
        N2
        F76
        S3
        F73
        W3
        S1
        R180
        W4
        L90
        F9
        E2
        F76
        N3
        F46
        N5
        F41
        S5
        L90
        N3
        L180
        F95
        E3
        R90
        F3
        E4
        F15
        S2
        W5
        R90
        N1
        F26
        F99
        S1
        F13
        F88
        W2
        L90
        W5
        R90
        F2
        E4
        R180
        N2
        F5
        N2
        R90
        S4
        E2
        L90
        F21
        N3
        R90
        E2
        E4
        S1
        W3
        F42
        N3
        E3
        N2
        E1
        L90
        S1
        L90
        F50
        S4
        F24
        S3
        F50
        L90
        F70
        L180
        F19
        N3
        L90
        F18
        L90
        F5
        F70
        R180
        N1
        L90
        E4
        S2
        W3
        N3
        F16
        W5
        F15
        S1
        L180
        S3
        F60
        R180
        F7
        R180
        W4
        R180
        S2
        N5
        W1
        F59
        R90
        S5
        W5
        F80
        L90
        R90
        F62
        R180
        S3
        R180
        W3
        L90
        N2
        F64
        E4
        R180
        S4
        R90
        E3
        F27
        W4
        R90
        F45
        L90
        N1
        E3
        F12
        L180
        F83
        S1
        W5
        L270
        E5
        F97
        F45
        N1
        F16
        F85
        E4
        S2
        N1
        E3
        F41
        N2
        R90
        S2
        R180
        E5
        L270
        F22
        W2
        R180
        N3
        R270
        E5
        L90
        S2
        W5
        F57
        L90
        L90
        F96
        E1
        S4
        W1
        L90
        W1
        F20
        N5
        E4
        R90
        W3
        S5
        R90
        W4
        R270
        F91
        E5
        L90
        F19
        L90
        S4
        R90
        S4
        F58
        R180
        F78
        R180
        F16
        W4
        R90
        F13
        S3
        E3
        F14
        N4
        E4
        R90
        W3
        S4
        W5
        F53
        R90
        S5
        F67
        L90
        W5
        F97
        R90
        W3
        F82
        N5
        F92
        E3
        R90
        F55
        W1
        F100
        E3
        F88
        S2
        E3
        N4
        E1
        F75
        F2
        L180
        E5
        L90
        F40
        N2
        R90
        F25
        N2
        L90
        E4
        F15
        L90
        S3
        W2
        R90
        E2
        R90
        S5
        L90
        F14
        W1
        L180
        F19
        R180
        W4
        S1
        L90
        F14
        N5
        N3
        L90
        N2
        F78
        L90
        N5
        E3
        N2
        F73
        S2
        F88
        N3
        F42
        S5
        F38
        S4
        F73
        R90
        F45
        R90
        F98
        L90
        W5
        S4
        L90
        E3
        L90
        F32
        N2
        F32
        W5
        S1
        R180
        F76
        W1
        L180
        W2
        S1
        R180
        S2
        F1
        S1
        E2
        R90
        N3
        E4
        L90
        S5
        L270
        N2
        F83
        L90
        F53
        L90
        F100
        W1
        L90
        E4
        R90
        S4
        F41
        W2
        R270
        E4
        F65
        R90
        F39
        E5
        R180
        F1
        W2
        R180
        E5
        L270
        E3
        R90
        W3
        R90
        W5
        F17
        E4
        F18
        S2
        N5
        E5
        L90
        W4
        F1
        S4
        E4
        L90
        S3
        E4
        F62
        R180
        F52
        S3
        F10
        S5
        L90
        F28
        R90
        F49
        W3
        F9
        N3
        W2
        N1
        L90
        L90
        E2
        S2
        W2
        F47
        R180
        E1
        R90
        F39
        E3
        N2
        W2
        F86
        F94
        R90
        S2
        R90
        E3
        F88
        N3
        E4
        L90
        S5
        F63
        W5
        L180
        N5
        N2
        F34
        R90
        F19
        N1
        F95
        L90
        E5
        L90
        F3
        R180
        S4
        L90
        F1
        N4
        R90
        E5
        N5
        R90
        E3
        F21
        R180
        S2
        R90
        N2
        L90
        N2
        W2
        E4
        S2
        E1
        N3
        R90
        W1
        L90
        F42
        W4
        F98
        W4
        L90
        F84
        S4
        F67
        W2
        S2
        L90
        S4
        E5
        L90
        F58
        E1
        S1
        W4
        N3
        W3
        R90
        E3
        F19
        S4
        L180
        N3
        R90
        N5
        F77
        N5
        W2
        S1
        L180
        F78
        W5
        S5
        E3
        N2
        F64
        L180
        F21
        R90
        W2
        F44
        N2
        L90
        F91
        S4
        R180
        N2
        W2
        F77
        S5
        F50
        R90
        F21
        W5
        L180
        S4
        F71
        E1
        N4
        R90
        F93
        W1
        N2
        F23
        E4
        L180
        F11
        E4
        F17
        R180
        N3
        R90
        F92
        R180
        F97
        S1
        E1
        F67
        E4
        R90
        F5
        F8
        L180
        L180
        L90
        N3
        N3
        R180
        E2
        F21
        L180
        N1
        S3
        L90
        W1
        L90
        E4
        R90
        F75
        R90
        W3
        F93
        L90
        S4
        F35
        F72
        S5
        E3
        F7
        S3
        R90
        W2
        R90
        F21
        W3
        N5
        F70
        W5
        N2
        L270
        F69
        L90
        S2
        F88
        W5
        S4
        R180
        E2
        F52
        N1
        F93
        N2
        E5
        S4
        L90
        N4
        R90
        F65
        S4
        L180
        S3
        F8
        E4
        R90
        F76
        E3
        F33
        R90
        N2
        F25
        N4
        E1
        S2
        L90
        N1
        L90
        N5
        R90
        E5
        F81
        N5
        F3
        W4
        S1
        F45
        W1
        F43
        E4
        N1
        F45
        E4
        S4
        E4
        N2
        E2
        F15
        W1
        L270
        F84
        R90
        N1
        F21
        N2
        F26
        W1
        N1
        W4
        S4
        F25
        R90
        N5
        R90
        S2
        R90
        E2
        L180
        N4
        R270
        S3
        F46
        S4
        S2
        L90
        N2
        F7
        W1
        S1
        L270
        E2
        R180
        S1
        W4
        N1
        F65
        L90
        E1
        N1
        R90
        N3
        W1
        F47
        R90
        F95
        S4
        L270
        F21
        W4
        S3
        F55
        E2
        N2
        F85
        N2
        R90
        E2
        F8
        S2
        W2
        R90
        W4
        F54
        E4
        F76
        N2
        F5
        F50
        N3
        R180
        W3
        S4
        E1
        L90
        S4
        W1
        F8
    """.trimIndent()
}