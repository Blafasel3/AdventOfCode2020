package advent.of.code.day24

import kotlin.math.abs

class FloorTile(
    var x: Int = 0,
    var y: Int = 0,
    var z: Int = 0,
    var isBlack: Boolean = false
) {

    fun moveWest() {
        x += -1
        y += 1
    }

    fun moveEast() {
        x += 1
        y += -1
    }

    fun moveNorthEast() {
        x += 1
        z += -1
    }

    fun moveNorthWest() {
        y += 1
        z += -1
    }

    fun moveSouthEast() {
        y += -1
        z += 1
    }

    fun moveSouthWest() {
        x += -1
        z += 1
    }

    fun flipTile() {
        isBlack = !isBlack
    }

    fun isNeighbor(other: FloorTile): Boolean {
        val xDist = abs(other.x - x)
        val yDist = abs(other.y - y)
        val zDist = abs(other.z - z)
        return this != other
                && xDist <= 1
                && yDist <= 1
                && zDist <= 1
                && xDist + yDist + zDist < 3
    }

    override fun toString(): String {
        return "FloorTile(x=$x, y=$y, z=$z isBlack=$isBlack)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FloorTile

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        return result
    }
}