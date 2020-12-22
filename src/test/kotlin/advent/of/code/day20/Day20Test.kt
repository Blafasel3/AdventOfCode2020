package advent.of.code.day20

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day20Test {

    @Test
    fun `part 1`() {
        val testInput = """
            Tile 2311:
            ..##.#..#.
            ##..#.....
            #...##..#.
            ####.#...#
            ##.##.###.
            ##...#.###
            .#.#.#..##
            ..#....#..
            ###...#.#.
            ..###..###
            
            Tile 2729:
            ...#.#.#.#
            ####.#....
            ..#.#.....
            ....#..#.#
            .##..##.#.
            .#.####...
            ####.#.#..
            ##.####...
            ##..#.##..
            #.##...##.
            
            Tile 1951:
            #.##...##.
            #.####...#
            .....#..##
            #...######
            .##.#....#
            .###.#####
            ###.##.##.
            .###....#.
            ..#.#..#.#
            #...##.#..
            
            Tile 1427:
            ###.##.#..
            .#..#.##..
            .#.##.#..#
            #.#.#.##.#
            ....#...##
            ...##..##.
            ...#.#####
            .#.####.#.
            ..#..###.#
            ..##.#..#.
        """.trimIndent()
        val tiles = testInput
            .split(Regex("\\s+\\n"))
            .map { tileAsString -> parseTile(tileAsString) }

        val tile = tiles.first()
        assertEquals(2311, tile.id)
        assertEquals(4, tile.borderLines.size)
        assertTrue(tile.borderLines.contains("..##.#..#."))
        assertTrue(tile.borderLines.contains("...#.##..#"))
        assertTrue(tile.borderLines.contains("..###..###"))
        assertTrue(tile.borderLines.contains(".#####..#."))
    }

    private fun parseTile(tileAsString: String): Tile {
        var lines = tileAsString.lines()
        val tileWithId = lines.first()
        val tileId = tileWithId.dropLast(1).split(" ").last().toInt()
        lines = lines.drop(1)
        val leftBorder = lines.map { it.first() }.joinToString(separator = "") { it.toString() }
        val rightBorder = lines.map { it.last() }.joinToString(separator = "") { it.toString() }
        return Tile(tileId, listOf(lines.first(), rightBorder, lines.last(), leftBorder))
    }

    private val puzzleInput = """
        Tile 2609:
        #....#.#..
        .####...#.
        #..#.##.##
        #..#......
        #.##..#...
        ##.#..#..#
        #...##...#
        ..#.##...#
        #....#....
        ..#..####.

        Tile 3347:
        .#.####...
        .#........
        .##......#
        .......##.
        #.#.....#.
        #.####...#
        #..##.#.#.
        #...#....#
        .##.....#.
        ###.##.##.

        Tile 2801:
        #####.....
        ....#.....
        #.##....#.
        #......###
        ....#....#
        ..#.##....
        .#.#.#..#.
        ..#.#..#.#
        ..........
        .####.####

        Tile 2851:
        ..#....#.#
        ..#####...
        .#..##.#.#
        ##..#.####
        #####.##..
        ..#.#.....
        ##.#.....#
        ###......#
        #.....#.#.
        ..#.###.#.

        Tile 2297:
        .##.##.###
        ##...#.#.#
        ...##..#..
        ..#.###.#.
        ##...#...#
        ....#.#..#
        #..##.#..#
        ##.#.#..##
        #...#..#..
        ##.##...#.

        Tile 1237:
        #....#.#..
        ..#.###...
        ..##.#..##
        #..#..##.#
        #..#.###..
        ...##.#...
        #...##.#..
        #...##..##
        ...#.#..#.
        ..#.####..

        Tile 1931:
        .#..#####.
        .#..#..#.#
        ..###..##.
        ....#.#.#.
        ##..#.####
        ###..#..##
        .....#...#
        ###.......
        .##.#.#...
        .#..######

        Tile 3989:
        ..#..##..#
        #....#....
        ...##....#
        ..#..#...#
        ##........
        #.........
        #........#
        ..##..#.#.
        .#####.###
        #####.###.

        Tile 1129:
        #..##..#.#
        #.......#.
        #.......##
        #.........
        ...#.###..
        #.#.###...
        ##.#.#.#..
        ##...###.#
        #..#####.#
        #.####..#.

        Tile 3491:
        ##..###.#.
        #.........
        ...###..#.
        #.#####..#
        ####...###
        ...###...#
        ..#.##.#..
        #.#.##...#
        ...#..#..#
        ###.#.####

        Tile 1607:
        ...##.####
        #.#.....##
        #.....#...
        #..##.#.#.
        ........#.
        #....###.#
        .....#..##
        #..#....#.
        ##.......#
        ...#.#####

        Tile 3307:
        ..##..###.
        ..#.##..##
        ###.......
        #..#....##
        #...#...##
        #.........
        #####.....
        #.##..#...
        #...#....#
        ...####.##

        Tile 3931:
        .##....#.#
        ##.#..#..#
        ##..###..#
        #.#.##....
        #...##..#.
        ##.##.....
        ..##....#.
        #....##.#.
        .#..#.#.#.
        ..#.####..

        Tile 2011:
        ...###.###
        .#.#......
        .#.......#
        ..#..#....
        ##.#.#....
        #.#.#...##
        #...#####.
        #...#.#...
        #..#......
        ..###.#..#

        Tile 1033:
        ..##.##.##
        #..#..##.#
        .....#.##.
        ........#.
        .....#...#
        .##....###
        #..#..#..#
        ##..###..#
        #....#...#
        ####.##.#.

        Tile 3761:
        ##.....###
        #...##.#.#
        ....#.#..#
        ###.#..#..
        #.####.#.#
        ..#.###..#
        ........##
        .###..#...
        ..........
        .##.#.####

        Tile 1277:
        #.###...##
        #..#......
        ..#.......
        .....#....
        #.....#..#
        #....#..##
        ##..#...#.
        #....#...#
        #....#.#.#
        .###.##..#

        Tile 1487:
        ##.....##.
        #.#.......
        .####..#..
        #...#..#..
        ##...#...#
        ##.....#..
        ##.#.#....
        #.....#.##
        ....#.....
        .....#..#.

        Tile 3617:
        ..#.##.#.#
        .....###..
        #.#......#
        ..#.#..#..
        ....#.#.##
        #...#.#...
        ###..##...
        ..#..##..#
        #........#
        .#.##.#..#

        Tile 3121:
        ###..#...#
        ..#......#
        #.#.......
        #..#......
        ...#.#...#
        .....#.#..
        #......#..
        .#.##..###
        .........#
        .##.#####.

        Tile 2017:
        .##..#....
        .#.##...##
        ...#..#..#
        #.#.......
        #.##...#.#
        #.....#..#
        ........#.
        #...#...#.
        ......#..#
        #.###..###

        Tile 3119:
        ##...#.###
        ##....#..#
        #........#
        .#..#...##
        .....#....
        #.#.......
        ##........
        ..#...#...
        ..###.....
        .#.#.#.###

        Tile 1933:
        ...##.#...
        #..#......
        .....#..##
        ##..#.....
        #.#..##...
        ....#.#...
        #......#..
        .##.......
        #..#......
        #.####.#.#

        Tile 1597:
        .#.##.#.#.
        ....#####.
        #..##...#.
        ........#.
        ....#.###.
        .....#....
        #.#..##...
        ......#.##
        ...#....##
        ...##.#.##

        Tile 2467:
        #.#.#.####
        ......###.
        .##..#...#
        .....#...#
        #......##.
        ......#..#
        #....###..
        ...#..#...
        ...#.#..##
        .#.####...

        Tile 1427:
        ##.###.#.#
        #.......#.
        ...#.#.#.#
        ##...#.#.#
        ...#.##..#
        .##...#...
        ##.#.#....
        .....#.#.#
        ..#......#
        ....#....#

        Tile 2351:
        .#.#..###.
        #.##..####
        #...#.....
        #........#
        #....#...#
        ..#.....#.
        #..#####..
        ..#.##..#.
        .........#
        #..#.#.#..

        Tile 2243:
        .....#.#..
        #.##..#..#
        .#.......#
        ...#......
        ##.#.....#
        ....#.#..#
        ##...#...#
        #...#.....
        .#....#.##
        ##.#.#.#..

        Tile 3163:
        .#.....#.#
        .#.....###
        ##.....#.#
        ..###..#.#
        .#........
        ###.#.##.#
        ..#..##.#.
        .....#..#.
        #.....#..#
        ..###.....

        Tile 2281:
        ##.#..#.#.
        ###....#.#
        #....#..#.
        .##....##.
        #..#...###
        #........#
        ...##.....
        ...#.....#
        .##.####.#
        ..#.###.##

        Tile 2213:
        ...####..#
        ...#..#...
        #....###.#
        ..#..#..##
        ###.#..#..
        ....##...#
        ##.......#
        .#..##.#.#
        .#..#####.
        .##...#...

        Tile 1097:
        #.##...#.#
        .....#.#.#
        ....#..#.#
        ...#......
        #......###
        #.........
        .#..#..#..
        .#.......#
        .......##.
        ...#...#..

        Tile 3821:
        .#....####
        ....#.#..#
        .#...#.#.#
        ...#..##.#
        #..#..#...
        #.#..#.#.#
        #.#.####..
        ##...##..#
        #......##.
        #..#.##.#.

        Tile 2699:
        #..#..#.#.
        #......#.#
        #........#
        ...#..#..#
        ....###...
        #.....##.#
        ...#...#..
        ....#...#.
        #.....#..#
        ###.#...#.

        Tile 1231:
        ..##..#.#.
        ..##.#.#.#
        .....#...#
        ..#.#....#
        #..#....#.
        .##..##...
        ...#..#.##
        #..#......
        #.#......#
        ###.######

        Tile 3631:
        #..#...##.
        #...#..#..
        .#....#..#
        ...##.....
        .....#....
        .........#
        ..#.......
        .#.###...#
        ...#..#..#
        .#..###...

        Tile 1913:
        ..#.##...#
        #....#....
        .#..##...#
        #.........
        #......#..
        ####......
        #.###.....
        ##........
        .#..#.....
        ##.#..##.#

        Tile 1013:
        .....##.#.
        ##.###....
        .###.#...#
        #........#
        ..........
        ..........
        #.#....###
        ....##....
        ###.#.####
        ##..###...

        Tile 1181:
        ####..#.#.
        #.#.#..#..
        .....###.#
        ##..#..#.#
        #.#.....#.
        #.##.#.#..
        ##....###.
        ..##.#..#.
        #...#.#..#
        .#.##.#.##

        Tile 2671:
        ...##.#..#
        .#...#..##
        #.#.......
        #.......##
        ..#......#
        #.##..#...
        #....##..#
        #..##....#
        .....#.#..
        .#.#....##

        Tile 1721:
        #...###.##
        #....####.
        ..#....#.#
        #....#....
        #..###....
        ##..#..#.#
        .##.###...
        #.......##
        #..#......
        ##..###..#

        Tile 1877:
        .###...#..
        ....#....#
        #..#.....#
        ......#.#.
        ..#...##.#
        #.#..##..#
        #......###
        ....#.#.#.
        #...#....#
        .##.####..

        Tile 1889:
        ..##.#.###
        ..#...#.#.
        ..#.##..##
        #....#..##
        #........#
        ##.#.#....
        ..##.....#
        ..##..#...
        .#.#..#...
        ..####.#.#

        Tile 1091:
        ..##...###
        .....#....
        .#.#.##..#
        #..#...#.#
        ##.##...#.
        #.......#.
        .......#..
        #.#..#.#.#
        ...#.#...#
        ..#...###.

        Tile 2161:
        ###...#.#.
        ..#.....#.
        ###.......
        ..#.#.#...
        ..####..#.
        ##....##.#
        ......##.#
        ...#.#...#
        .#..#.#..#
        #.#...#.#.

        Tile 3253:
        ....###.##
        #......#.#
        #...#.#..#
        ..#..#....
        ...#.....#
        #...##....
        ..##...#..
        ..#.......
        #....#...#
        ..#.......

        Tile 3079:
        #..#.#.#.#
        #.#.......
        #..#..#.#.
        #.#.......
        ...##.####
        ...##.....
        ....#...##
        ...###.#.#
        #.#..#...#
        #.#.##.###

        Tile 1201:
        #.#..##.#.
        ........##
        #..#..##..
        ..###....#
        .##......#
        .....#...#
        #........#
        #..#...###
        #....#.#.#
        .#..#..##.

        Tile 3797:
        #.#.#..#.#
        ##.#..#.#.
        ..#.#...##
        .###..#...
        #..#......
        #..##..#.#
        ##.###...#
        .....##...
        #..###.#..
        ##.##.#...

        Tile 2311:
        ......#.#.
        ##.....##.
        .#.#.....#
        #.#...#..#
        ....#.###.
        ....#...#.
        #.#....##.
        ###.#..#.#
        ##.#..#..#
        ....#..##.

        Tile 1187:
        .#..##.#.#
        #........#
        #.#.#....#
        .##.#..#..
        ##....##..
        ##.....#..
        .#.#..#..#
        .#......##
        #.#......#
        .#######..

        Tile 3259:
        #.#...##.#
        ...##.....
        ......#...
        #..####...
        #...#.....
        ###......#
        ..#..#...#
        ##........
        #..#..##..
        ...###.#.#

        Tile 3019:
        #...##.###
        .##......#
        #..#....##
        ##.#.....#
        #.#..#...#
        ###......#
        ...#.#.##.
        #.......##
        .#..##...#
        ..####....

        Tile 1979:
        .#####.#..
        ...###.#..
        #.###...#.
        #....##.#.
        .#..#...#.
        ...#.....#
        ...#.#....
        ##..#.#...
        ..#...#.##
        ##.###..##

        Tile 2287:
        ##.####..#
        #..#......
        #.........
        ..#...##..
        #.##.#.#..
        ....#.#...
        ##....#..#
        ..#.#....#
        ....#...##
        ##..#..###

        Tile 2437:
        .#..#..#..
        .#.#.#...#
        #..#....##
        #..##..###
        .#..##.#.#
        .....##...
        .##..#..##
        #.#.#.#..#
        #......#.#
        #...###.##

        Tile 1571:
        #.##.#...#
        #.###.#..#
        #...#..#..
        ..#..#..##
        .###..#..#
        ###.#.#..#
        #..###....
        ...#....##
        .#..#.#...
        #.##.####.

        Tile 2833:
        .##.##....
        ...#.#....
        .#.##..##.
        #.....#..#
        #.....##..
        ..##...#.#
        ###....#.#
        ..........
        #.........
        ..#.#.#.##

        Tile 2861:
        .#..#.....
        ..#......#
        ..#..#..#.
        .#......##
        ###..#.#.#
        #.......#.
        .#....#...
        ....##...#
        #...#..#..
        #.#..#.###

        Tile 3361:
        ...#..#.#.
        ....#.....
        #...#....#
        #.##...##.
        ##.......#
        #........#
        .#.....#..
        #......###
        .#.#.....#
        ##.#...##.

        Tile 3929:
        ..####.###
        #.##....#.
        ##........
        ...#......
        #.....##.#
        ...##.##..
        ..#......#
        .##.#.....
        ##.#...#..
        .##.######

        Tile 2711:
        ###..###..
        .##...####
        .......##.
        .##.....#.
        .#....#...
        #.....#...
        .###....##
        ...#.....#
        #..#...###
        ##..###...

        Tile 2789:
        #.####...#
        #.......#.
        ##....#.#.
        #..###...#
        #.#..#....
        ....#####.
        ##..#..#..
        #...#....#
        #.#...#...
        .#....#..#

        Tile 3511:
        .#.#......
        #.###..#.#
        #..#.....#
        #....#...#
        #....####.
        ........##
        ..#...#.##
        .#.....#.#
        .##...#...
        .#....##..

        Tile 2417:
        ..##.#....
        #.#..#....
        ....#.#..#
        #.####.##.
        ..#..#..#.
        ##.##.....
        ....#....#
        #.........
        ..#..#...#
        ....#..#..

        Tile 1453:
        ...######.
        #.......##
        .##....###
        .##..#...#
        .......#..
        #.....#...
        ##.....#..
        ##..##...#
        ..#......#
        .##.#..###

        Tile 3727:
        #.#...#.#.
        #.#.#.....
        #####.#.##
        #.##...#.#
        #....#.##.
        #.......#.
        ..#...#...
        ......#.##
        ##....#.##
        #..###.##.

        Tile 1061:
        ..#.#..#.#
        .......#.#
        ##....##.#
        .#.....#..
        #.##...#.#
        .....#..#.
        ...#......
        #.....#..#
        ##....#..#
        .#.###..##

        Tile 2389:
        #.#.##.#..
        ..#......#
        #......###
        ...#.#....
        .#........
        ......#.#.
        ...##..###
        ....##...#
        ...###....
        ###.###.##

        Tile 2113:
        ##.#..###.
        ###.#....#
        ..#......#
        #...##...#
        ...#...#..
        #..#..#.#.
        .##.......
        .##.#..##.
        .#.......#
        ###.#.#.#.

        Tile 1741:
        ##..#...#.
        #.....#...
        ...###...#
        #..#...##.
        ....###...
        #...###...
        .....#.#..
        #..##..#.#
        #..#..##.#
        #.#.##....

        Tile 1283:
        ##....#.#.
        .#..#.##..
        ##..#.....
        .....#...#
        #.........
        ..#..##...
        #..#....##
        ...#.#####
        ...#....##
        #..#.#..#.

        Tile 1901:
        #######.##
        #.........
        ###..#...#
        ##.##.#..#
        .....#..#.
        #....#...#
        .##.....#.
        ........##
        ...#####..
        #.##.##.##

        Tile 2357:
        ...#......
        #..#.##...
        ...##.##..
        #...##..##
        #...##.#.#
        #.##.....#
        .....##...
        ..#...#...
        .....##.##
        ...#.#....

        Tile 3529:
        .##..#.##.
        ..#.#..##.
        #.#.....##
        #..##...#.
        ##...###..
        ....#..###
        #.#.....#.
        .....##..#
        .#.......#
        ######..##

        Tile 1039:
        ...##.#.#.
        #......#..
        #.......##
        ##.#.##..#
        ......#..#
        #....##..#
        ##.##.#...
        #...#...##
        ...#..#..#
        ####.#.#..

        Tile 2963:
        ..#.##...#
        #.........
        ..##.....#
        ...#..#.#.
        #..#.....#
        ..#..#....
        ....#.....
        #...##.#.#
        ##.##..#.#
        ####.###.#

        Tile 2557:
        ##.#..##..
        ....#....#
        ....#.....
        ##.##.#...
        ..#.##..##
        #..#....##
        #..#.#..#.
        #.......##
        #.........
        .###.###..

        Tile 1523:
        #.#.###..#
        #.#...#...
        .........#
        #...#.#..#
        ..#...#..#
        #..#.##...
        ..###.#...
        #....###..
        #.#....#.#
        #.##..#.#.

        Tile 1789:
        .##....###
        ##..#.....
        ..#.#..#.#
        ##.#.#..##
        ...#.##..#
        #..#......
        #..#.#.#..
        ##..#.....
        #####.#..#
        #..#....#.

        Tile 3049:
        ....#.##.#
        #.#.....#.
        #......##.
        #.##.....#
        ..#.#....#
        #........#
        #.#...#..#
        #.#...#..#
        #..#..#..#
        .#.#.#..##

        Tile 3719:
        ..#...##..
        ##..#....#
        ...#....##
        #..#.##...
        .##.#..#..
        #...#####.
        #.##.#..##
        ..#.##...#
        #.....##.#
        .##.#..##.

        Tile 2069:
        #.#.#.#...
        #...##....
        ##..#.....
        .#.#...#..
        #.......#.
        .#...#..#.
        .#..#.....
        ###.....#.
        #.#...##.#
        #.#.#...##

        Tile 2083:
        .#.#######
        ...#..#.##
        ..###..#..
        ....#.....
        #....####.
        ......#..#
        ##........
        #...####.#
        ..#..##...
        #.#..##.##

        Tile 3671:
        #.....###.
        #....#....
        ..#.......
        ..#.#....#
        ....#..#.#
        ..##....##
        #...#.##.#
        ##.#..##..
        #..#......
        .##.#.####

        Tile 1103:
        ...#.##...
        #..##.##.#
        ...##....#
        #.....#..#
        .##..#.###
        ##.......#
        .........#
        ....#..#.#
        ##.....#.#
        #.#..#..##

        Tile 3643:
        #..####.##
        ..#...#.##
        .#.##..#..
        ..#.#.....
        #....#..#.
        ..##..#...
        #.#....###
        ..###.#..#
        #..##.#..#
        #..##.#..#

        Tile 3499:
        .##.#...#.
        #..#####..
        ...#..##..
        ...#.....#
        #.#....#..
        #.##..#..#
        #......#..
        ##..#..#..
        ##...#####
        .##.###...

        Tile 2503:
        ..#####..#
        .......##.
        #....#....
        #.##...#..
        ..#......#
        .##.#....#
        ###.#....#
        #....##...
        ......#..#
        ###..#....

        Tile 2729:
        .###.##..#
        #.....#...
        #.#....##.
        #....##..#
        #.........
        #..#....##
        ##..##.##.
        .#......#.
        #.#.##....
        ##.....###

        Tile 2221:
        .#.###.#.#
        #...#....#
        #....##...
        ......#..#
        ...#..#...
        .....##..#
        ..#.##....
        .##..##.##
        ....####.#
        #####.....

        Tile 2129:
        #..###..##
        #..#.#.##.
        .#.#.....#
        .#..##...#
        #..#.#.#..
        ...#......
        .#...###.#
        ....#.#...
        .#.....#.#
        ..##.##..#

        Tile 1217:
        #######.##
        #.......##
        ......##..
        ##.##.#.##
        .........#
        #....#..##
        ##..#....#
        #.....#..#
        #...##....
        .######.#.

        Tile 3967:
        ##...#..##
        #.....#...
        ..####...#
        ##....####
        ...#..#...
        .....#...#
        #.....#...
        ##....#...
        .#...##.##
        ...#.#####

        Tile 2339:
        ..#..###..
        #....#..#.
        #.......##
        .....#..#.
        ..#....#.#
        .#..#.##..
        #..#..#...
        #...#....#
        ##.#..#..#
        .#####....

        Tile 1579:
        #.####.#..
        .......###
        #........#
        .#......##
        #..##....#
        ..#.#....#
        ...#..#..#
        #.#......#
        ..#.##.##.
        ...##.#..#

        Tile 2683:
        ...#####.#
        ##.##.##..
        ..#...##.#
        .#..#.....
        ......#...
        ....#.#..#
        #...#.#...
        ###.##....
        ..##.....#
        ..###.#.##

        Tile 1993:
        #.##.##...
        ....#....#
        ...#.#...#
        ..#...##..
        #..####...
        ###.#.##..
        ##.......#
        #...##....
        ..#..#....
        ######..#.

        Tile 2777:
        ###.#.#..#
        .###.....#
        #.#.....#.
        .#.#.##.##
        #.##....##
        ##..#.#..#
        .#...#..#.
        #..##..#..
        #.##..#.#.
        ##.#.####.

        Tile 2081:
        .#.#.....#
        ##.#...###
        #....#....
        #.#..#..##
        .........#
        ##.##..#.#
        #.##....##
        #.#...#..#
        ....##...#
        ####.##.#.

        Tile 1009:
        ###.#.....
        #..#.#.#..
        ..##...#.#
        ..##......
        #...#..#..
        ##.#..#...
        ..##..#...
        #...#..##.
        ##.#...###
        #.##.#...#

        Tile 1543:
        #.#.#..#..
        #....#...#
        .....##.##
        ....#...##
        .....#..#.
        #.....#.##
        #....##.##
        #..##.#...
        ..#####..#
        #....####.

        Tile 3877:
        ..##.#.##.
        ...##.#...
        #....#....
        .##....#.#
        ##......#.
        #...###..#
        #......#.#
        .#......#.
        ....#.#...
        #.#.#.####

        Tile 2857:
        ..#.#.###.
        ...#...#.#
        ........##
        ##...#..#.
        .#.....#.#
        ...#......
        #.........
        #.#......#
        #..#......
        ...##.###.

        Tile 1481:
        ##.###.#.#
        .#........
        #.....#.#.
        ####..#..#
        ...#....##
        #.##.#....
        ......#...
        #.####....
        #.......#.
        ##...#...#

        Tile 1583:
        .#.#.#.#.#
        ..#.......
        .....#.#..
        ##...#.#..
        ..#.......
        ...#......
        ##........
        #........#
        ..#......#
        #..####.#.

        Tile 1861:
        ##.##..#..
        .....#....
        #..#..##..
        #.....###.
        #..#.....#
        ...#..##..
        ##...#..#.
        ..........
        ......##..
        ###...####

        Tile 2099:
        ..##....##
        ..#.#.#...
        #########.
        ..###..#..
        ...##.#.##
        ....#.#...
        .#.###.#..
        ....##...#
        #..###.#..
        ###.##..#.

        Tile 2819:
        .##.......
        ....#.....
        ....####..
        .#...###..
        #.#..#....
        #####....#
        .#...#.#.#
        #..#.#....
        #...#..###
        .#.#.....#

        Tile 3803:
        .....#.###
        #..##.....
        ....#.....
        #...###.#.
        ...#..#.#.
        ..........
        #.......##
        #...#.###.
        #........#
        #.##.....#

        Tile 2803:
        ....#.#...
        ....#.....
        ....#.#..#
        #.#..#....
        .#.##..###
        #..##.#.#.
        ..##..#...
        ##.##.....
        ...#.....#
        ##.#....##

        Tile 1747:
        #..#..#.#.
        #...#...#.
        #..#.....#
        .#.#.##.#.
        ###.##..#.
        ...#....##
        .........#
        .........#
        #......###
        #.#.##..##

        Tile 2137:
        ......#...
        #..##...##
        #..###....
        #.##..#.##
        ....#....#
        #..#...##.
        ....###..#
        #......#.#
        ..#.....##
        ..###.##..

        Tile 1847:
        .###.#.###
        ......#..#
        ##.##...#.
        #.....##..
        ##..#....#
        .#.....#.#
        ...##.#...
        .#..#...##
        #...#.##..
        .#.#.#...#

        Tile 2381:
        ..#.#..#..
        ..#####...
        .##.#.....
        ..........
        .....#....
        ##.#...###
        #.##..#...
        ##.#####.#
        ..##...##.
        ...###.#..

        Tile 3659:
        #.#.#.##..
        ##.##....#
        #.##......
        ##.......#
        #.#.#.....
        ##...#...#
        #.#.#....#
        #..#.##..#
        ##.##..#..
        .#.####.##

        Tile 2207:
        ....##.#..
        .#..#.#...
        #....#...#
        #.#.#..###
        #....#...#
        ##.#.####.
        .####.##.#
        ##..####.#
        #.##.#..#.
        ###.###..#

        Tile 2879:
        ##.....##.
        .#.#.#...#
        #.....#..#
        #....##.##
        ..#.......
        #.#...#...
        .#....##..
        #.....#.#.
        .##..#..#.
        #.#######.

        Tile 1321:
        .....##...
        #.#...##.#
        #...##.##.
        #...#.#..#
        ##..#.#..#
        ...#......
        #.####..#.
        #.........
        ..#.....##
        ########.#

        Tile 3331:
        #.##..####
        #..#.#.#..
        ..#....##.
        #..#.##..#
        .......#..
        #..#......
        ..#.#.#.##
        #....####.
        #..##.#..#
        ...#.##.##

        Tile 3623:
        ###..###..
        .##.#.##..
        ..###.....
        #.#.#...##
        ##.#..#.##
        .##.#.....
        ...#......
        .#..#..#.#
        ##....####
        .#.#.#...#

        Tile 3637:
        ..##.##.##
        .##.......
        ........#.
        ......##..
        .#..#.##..
        #....###.#
        ....#..#.#
        .#.#.#..##
        ....###..#
        ....###.#.

        Tile 2677:
        .#.#.#.#..
        #........#
        ....#.##.#
        ....######
        ##.#.##...
        ...#.#..#.
        ##......#.
        ..#.......
        ..####....
        ....####..

        Tile 3299:
        #...#..#..
        ....#.....
        ##.###..#.
        #.........
        #...##.#..
        #...#..#.#
        .#....#..#
        ##......#.
        ..#.#..#.#
        #...###.#.

        Tile 2647:
        #..#.#.#..
        ###.##..##
        ..#......#
        ....#.....
        ....#.#..#
        #.#..#..##
        .#..#.###.
        ......##.#
        ##......#.
        #.#..#.##.

        Tile 3943:
        ###.######
        ..#.......
        ##..##..#.
        ..##..#..#
        ..#..##.##
        .#..#..##.
        .....##.##
        #.##...##.
        .#..####..
        .#..#.#..#

        Tile 2593:
        #.#.#....#
        .....#..#.
        #...#.....
        #.#..###.#
        #...##....
        ##.#.#....
        ..#.#.#.##
        .##.#.....
        #.#......#
        ..##...#.#

        Tile 3181:
        ....######
        ..........
        .##....#.#
        ...#....##
        .#.##.#.##
        ###....##.
        ...##...##
        .#....#..#
        ....#.#..#
        ..#.#...##

        Tile 3697:
        #.#.##....
        .#....##..
        .#.#......
        #....##.#.
        #....###.#
        ...#.#.#..
        .##.#.#..#
        #...#....#
        ..#.#...#.
        .##.###...

        Tile 1213:
        ..#.###.#.
        ##.#...#.#
        #.###.....
        ..##...#..
        ..#...#...
        .....##.#.
        #.#....#..
        ..........
        .##...#.#.
        #######.#.

        Tile 2053:
        .#.###..#.
        #.#.#....#
        .........#
        ..##...#.#
        ..#..#...#
        ##..#.####
        #.##.#.#..
        .##.#.##.#
        #...#.##.#
        #...#.....

        Tile 2549:
        #.##..#..#
        #.#....#.#
        ....#.##.#
        .#..#....#
        ..##....#.
        ##...##...
        .#.....##.
        ..#....###
        .......##.
        #...##....

        Tile 1451:
        ....##.###
        ..#....###
        .##..##.#.
        ..........
        ......#.##
        #....##.#.
        #..#..#.#.
        .....#..#.
        .##....##.
        .###.#.#.#

        Tile 1613:
        #.#.#.###.
        .####..#.#
        ....#...##
        ##......##
        #..#......
        .##.#.....
        .###....##
        ......#..#
        #....#.##.
        ...######.

        Tile 3301:
        ##.#.###..
        .#...##...
        #.##...#.#
        ........#.
        #.#......#
        .###..#.##
        #.#.#..##.
        #.........
        ......####
        .###.#.##.

        Tile 1787:
        ###...####
        #.##..##.#
        ...##.#.#.
        #.........
        .......#.#
        .#..#...##
        .##.#..#..
        #...#....#
        #..#.....#
        .#...##.#.

        Tile 1051:
        .........#
        ...#...#.#
        #...###...
        ....#.....
        .#.......#
        .#.......#
        ...##.##..
        ###.##.#..
        ##..##....
        ..#...#.#.

        Tile 3041:
        #.#.....#.
        .##....#..
        .#....#.#.
        .####...#.
        #...##..#.
        ..##......
        .#.#.#...#
        .....##.##
        .#.#.###..
        .#.#.#.#.#

        Tile 3691:
        ####.#.###
        #.####..#.
        #.###.....
        ...#.#.#.#
        ..##....##
        .#.....#.#
        ###...#..#
        .#.....###
        #.####..##
        ...#####.#

        Tile 3319:
        ..##.#.##.
        ..#.....##
        #......###
        ##..##.#..
        ......#.#.
        ...####...
        #.........
        #.........
        ##....#...
        ##....###.

        Tile 1381:
        ##.#.##...
        .#....#...
        #..##.#..#
        ..#.#.....
        ....##....
        ##.###....
        #....##..#
        .#....##.#
        ..#..#...#
        #.#.....##

        Tile 3673:
        #.....##.#
        ###..#....
        ...#.....#
        ##...#...#
        ##...#...#
        .#........
        .....#...#
        #.#..#....
        #..#.....#
        ####.##...

        Tile 1493:
        ###..#..#.
        #..#......
        .........#
        ....#.....
        ..###.....
        .#.#.#...#
        #..###.###
        .##...#..#
        #....#####
        #.#..#.#..

        Tile 1327:
        #..#####..
        ....##..##
        ...#...##.
        ......##..
        ...#....#.
        ..#.#.....
        ..#.#.....
        ##....#...
        .##....#..
        .####.###.
    """.trimIndent()
}