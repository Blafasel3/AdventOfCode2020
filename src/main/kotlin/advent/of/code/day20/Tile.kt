package advent.of.code.day20

class Tile(val id: Int, val borderLines: List<String>) {

    fun rotate(): Tile {
        val newBorderLines = listOf(
            borderLines.last().reversed(),
            borderLines.first(),
            borderLines[1].reversed(),
            borderLines[2].reversed()
        )
        return Tile(id, newBorderLines)
    }

    fun flipVertically(): Tile {
        val newBorderLines = listOf(
            borderLines[2],
            borderLines[1].reversed(),
            borderLines[0],
            borderLines[3].reversed()
        )
        return Tile(id, newBorderLines)
    }

    fun flipHorizontally(): Tile {
        val newBorderLines = listOf(
            borderLines[0].reversed(),
            borderLines[3],
            borderLines[2].reversed(),
            borderLines[1]
        )
        return Tile(id, newBorderLines)
    }

    override fun toString(): String {
        return "Tile(id=$id, borderLines=$borderLines)"
    }
}