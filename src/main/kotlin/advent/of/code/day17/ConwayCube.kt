package advent.of.code.day17

class ConwayCube(val coordinates: ThreeDimensionalCoordinate, var isActive: Boolean = false) {
    fun isDirectNeighbour(other: ConwayCube): Boolean =
        coordinates.isDirectNeighbor(other.coordinates)

    override fun toString(): String {
        return "ConwayCube(coordinates=$coordinates, isActive=$isActive)"
    }
}