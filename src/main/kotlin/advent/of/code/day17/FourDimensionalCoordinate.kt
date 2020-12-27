package advent.of.code.day17

import kotlin.math.abs

class FourDimensionalCoordinate(x: Int, y: Int, z: Int, val w: Int) : ThreeDimensionalCoordinate(x, y, z) {

    fun isDirectNeighbor(other: FourDimensionalCoordinate): Boolean =
        this != other
                && abs(other.x - this.x) <= 1
                && abs(other.y - this.y) <= 1
                && abs(other.z - this.z) <= 1
                && abs(other.w - this.w) <= 1

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as FourDimensionalCoordinate

        if (w != other.w) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + w
        return result
    }

    override fun toString(): String {
        return "4Dimensional(x=$x, y=$y, z=$z, w=$w)"
    }
}