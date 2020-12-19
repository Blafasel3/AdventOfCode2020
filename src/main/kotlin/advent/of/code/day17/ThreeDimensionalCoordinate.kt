package advent.of.code.day17

import kotlin.math.abs

class ThreeDimensionalCoordinate(val x: Int, val y: Int, val z: Int) {

    fun isDirectNeighbor(other: ThreeDimensionalCoordinate): Boolean =
        this != other && abs(other.x - this.x) <= 1 && abs(other.y - this.y) <= 1 && abs(other.z - other.z) <= 1

    override fun toString(): String {
        return "ThreeDimensionalCoordinate(x=$x, y=$y, z=$z)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ThreeDimensionalCoordinate

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