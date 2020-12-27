package advent.of.code.day24

import org.junit.Test

class Day24Test {
    @Test
    fun `Part 1 - Laying the ground floor`() {
        val blackTiles = getBlackTiles(puzzleInput.lines())
        println(blackTiles.size)
    }

    @Test
    fun `Part 2 - Living Art exhibit`() {
        val testInput = """
            sesenwnenenewseeswwswswwnenewsewsw
            neeenesenwnwwswnenewnwwsewnenwseswesw
            seswneswswsenwwnwse
            nwnwneseeswswnenewneswwnewseswneseene
            swweswneswnenwsewnwneneseenw
            eesenwseswswnenwswnwnwsewwnwsene
            sewnenenenesenwsewnenwwwse
            wenwwweseeeweswwwnwwe
            wsweesenenewnwwnwsenewsenwwsesesenwne
            neeswseenwwswnwswswnw
            nenwswwsewswnenenewsenwsenwnesesenew
            enewnwewneswsewnwswenweswnenwsenwsw
            sweneswneswneneenwnewenewwneswswnese
            swwesenesewenwneswnwwneseswwne
            enesenwswwswneneswsenwnewswseenwsese
            wnwnesenesenenwwnenwsewesewsesesew
            nenewswnwewswnenesenwnesewesw
            eneswnwswnwsenenwnwnwwseeswneewsenese
            neswnwewnwnwseenwseesewsenwsweewe
            wseweeenwnesenwwwswnew
        """.trimIndent()
        val blackTiles = getBlackTiles(testInput.lines())
        for (day in 0..10) {
            println("Day: $day")
            println(blackTiles.filter { it.isBlack }.size)
            blackTiles.filter { blackTile ->
                blackTiles
                    .filter { blackTile.isNeighbor(it) }
                    .filter { it.isBlack }
                    .count() !in 1..2
            }.forEach { it.flipTile() }
        }
    }

    private fun mapToMovements(line: String): List<Int> = line
        .replace("sw".toRegex(), "5")
        .replace("se".toRegex(), "4")
        .replace("ne".toRegex(), "2")
        .replace("nw".toRegex(), "1")
        .replace("e".toRegex(), "3")
        .replace("w".toRegex(), "0")
        .split("")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }

    private fun getBlackTiles(lines: List<String>): MutableSet<FloorTile> {
        val blackTiles = mutableSetOf<FloorTile>()
        lines.forEach { line ->
            val movements = mapToMovements(line)
            val floorTile = FloorTile()
            movements.forEach {
                when (it) {
                    0 -> floorTile.moveWest()
                    1 -> floorTile.moveNorthWest()
                    2 -> floorTile.moveNorthEast()
                    3 -> floorTile.moveEast()
                    4 -> floorTile.moveSouthEast()
                    5 -> floorTile.moveSouthWest()
                }
            }

            if (blackTiles.contains(floorTile)) {
                blackTiles.remove(floorTile)
            } else {
                floorTile.flipTile()
                blackTiles.add(floorTile)
            }
        }
        return blackTiles
    }

    private val puzzleInput = """
        swswwneswswswswseswewwswswsewseswneesw
        nwenwnwsenwnwsewswnenw
        swswwswswswewswswnewswsewwswswwsw
        nwswswswswwneswsweswenwswswwsewswswswsw
        sewnwnwwwnwenwnwsenwnwwwnw
        sesesesweswseeseswnwswwswwseseneswnw
        wsenweneswseseseeeeseeneeseeeese
        neenwneneseneeneeeneseswnwnweesenene
        swneswsewwwwwwswwswwswneswswwsw
        swwwswswswweswswnewswwswsewnwswwsw
        eswnweneeenwnwnenweeseeeweswswee
        wneneseswswwseseswswnwseeeswseseswsw
        wnwesenwswnesewnwneeswenwnwnenwnwwnw
        swswswswswswswseswesenwseswwswweswnwne
        nwwenwswnwnwnwwnwnwnwenenwnwsenwnwnenw
        nwwnenwwnwnwenwwnwwwwsewnwwsew
        wsenwwswwswesweeseneswnwswne
        wsenwnewnwnwseenwnwsenewsesewewne
        nwsesenwwnwsenwnwnwnwnewswenwnenwnwnwne
        eesenwnewesenwneswseeseseeesweew
        seseswsenenwswswswseswswswswseseeswwsw
        nenwnenwnwenwswnwnwnwne
        nwswsenwseewswsenwseseesweswnesenw
        wnwwswenwsewwnwwwnwnww
        seswnwsenwnwseswseseswseseeseseseenwse
        esewswwwswwnewswwwwswwwwnww
        eneseeeeeneewseneeneenweeee
        wneswwswwswswnwwwswswswneswsewswswe
        swswneswswsweswswswwwnwsweswswswswswsw
        nesenenwnwnesewnwnenwswnesenwnwnenwnwne
        neneswnweneeenwnwwnwseseeeneewswsew
        nenwseneeseneneswneeeeeeneenwenw
        neeswswnwseswswsesenwswswswswneseneswse
        wswewswwswwsewnwwneswwswswsenew
        nwnenwseswneseswswswswnwsesesweneenw
        sesweseseseseseswnwseseseeseeesenwse
        wneneswneenwwewnesenenweneneew
        swswseseeneseswswswsenwswswsesesenwsesw
        seewwwwwswwneswswswswnewwwnwnwsw
        swwwsewwwnenewwnenwnwsewneseswe
        neneseswsesenwseswwswsweswseswswseswswsw
        swwnwwswswswwwswneswwwwwswewe
        enewewneeseeeeeeeseneweesee
        swnwnewnewswswswseswnweswswnwsene
        swenwneeeneeesenwseesewneenwwneee
        wnwnweswwnwwwnwswewwnwseswneew
        swseeeeseeenewweesesenweese
        nwsenenwnwnwsenwwsenw
        eesewwwseeeneeseneeseeenweese
        eeneeswneswneesweenenwneneneneenew
        senwenenwswswseseneeswswswwwwsenwwenw
        wwwnwnewwwenwwnwnwsewnweswsewse
        nwnwnwnwnwnwnwnenwswnwnwsenwnenw
        neseeenewnewsenenew
        senwnwnwwnwnwnwswnewnwnenwwnwnwnwsenw
        seswseeseseswnewseneswseswwnwseneswne
        seseseseseneneseswseew
        nwneenenwsweneeeweswseswenenenese
        wnwnwnwnwwnwnwnwewswswnenwnwwnwww
        wwnwswwneswswwnewwwsewseeenww
        wnwenwsenwnenwnwnenesww
        senewneneneneneseneswnweneneneneneee
        sewnwsenwnwnwsenenwwnwwewwnwwnww
        eseneswwwswswswswswswenwswsweswswsesw
        nenewswweseeneeseeweeeeneswnwsw
        swswnwwwsewseswswswnwwswseswwneswsw
        nwnewnesenwnesenwwnwnwnewenwnewsenesw
        seeswnwenwseseewseeeseneeswseesenwsw
        swswseswswswsweseswswsenwswswnwswswseese
        wswswwnwnwwswswseewswwweneswnwwse
        eswseswswswwwwswnwwnwewnwwneww
        swswsesenwneweseenweeseeswnwnweswse
        wwwswneswwnewwswswswwwewwnwsew
        wneswnenenewnenenenenenweswnweenew
        enwnwnwnwnwnewnwnwnwnwenwnwnwnwwnw
        eeeesenweeeeeesewewenwee
        seseseeseeseseenwnwsesesesesenweswse
        sesewnweenwnesenwnwswnwnenenwswwnesenwnw
        swnwseswwswwwwwnenwswswsenwswewne
        weseswsewswswseseneseseneesesesewse
        senwwnewnwnewsenewseswswwwwswseww
        swnwneneswswwneesenenwenwwneswneneeee
        wswnwswnwneswswwsweswseneswsweewsenw
        seneseseneswseweesenwseseenesewesese
        seeesweeenwenweseseseeeeseee
        seseseeseeenwnwseseseseswsesesewsesese
        swnenwswenwnwnwenwnweswswweneswsenwne
        swswnenwnenweneneseeewnenwnwswnwswnenw
        swseseseseseseesesenenwseswseseseseseswnwsw
        eneseeseseeseseeseseewse
        swnenwswwswseswswswswswswswswswwswsew
        eswnenewswswnweeeeeeeeeeseee
        swwneswsenwnwsewnwnenewnwnwsenw
        nweneeesweeneneneneswneeneenenee
        eeeeseneeswew
        nwnwnwnwnewnwnenwnwnwnenwnenwe
        newswneswswsewnwswswswwwswswswseswsw
        nweswswswseswseswseseswswnw
        sesenesesesewseeseseweeesesewsew
        swseseswswswswswneswseswsw
        neseseswseseswsewwseneseswsenwewsesw
        eenwwwwswswswswnenwseswswseswswww
        seseseswseseneseeseseseee
        esweesenweeesenenweesesweeee
        eswseeewseeeeeeeesewewwenw
        nwewwsewwwwsenwwnwswnewwwnwnw
        swsewwwwswswswswnwwwwswsweesww
        wsewswsenwneeswwwwenwwswweswswwne
        eseswseneesesenwseesesewesesesene
        neeeswnwseeswnweesenenwenenewene
        seneneseeneeeneeeeenwneeenenew
        eneenwenweneneneneneeneseswwse
        neeesenwswswwwswnwsewnwswwwswswsw
        swnwswswswseswswneseswswswsesenewswwswne
        neneneeneneneneswnesenwswneneneneeneene
        nwswnenwnwnwnwnwnwsenewnwnenwnenwnwnwnw
        weeeeeeeeneeeesweeeeeew
        nwnwnenwnwnwnwnwwnwswesenwnwnwnwnwnwnwnwse
        nenwwwnwnwnwnwnwnwneswnwnwnwnenwnesee
        wswnewneneewesewweseeswnewnwwsew
        nwneseswnewneneneneneeneneswnenwwnenene
        senwesenenewnwnenwnwnwnwnenwwnwnw
        senesesweneeewneseswsewe
        swwwwnewswwswwswswesewwswswswnw
        weenwnwnwseneneswseenwnwnwnwnenwswnenene
        seswnwesweeeeenweesee
        wswwwwwwswewswsewnwswwnewwww
        wwwwwnwsewnesewwwwnenwsewsenwwe
        seswnewsenwwnwwesw
        seenwsesesenwswsenwsenweswswswswnwswenw
        seswnenwneneswsenwswwwesw
        wnwwwwwwewnwwsw
        swsenwsenwnwnenwwenwwswseseewsenww
        enwswnwnwnwnwnwnwnwsewnwswsenenwneenew
        neneseneewnenwneneenewnenenenwnenenene
        nwnwwnewnwnwnwnwseeswswsenesenwwnwne
        swneeswswnwswsenwnwswseneseeeswswswne
        swweswneswswneswsesewnw
        swwswenwneswseseswsesesesesenwseswnese
        wnwnwseweenenwnwsenwswneenwnesenene
        swswseseswswsewswswseesesesw
        neseeeewwnwnenwseeeeswee
        swnwneeswwneswswsewswewswsewswwwww
        nenwesesesesesewwneswsweseweesesee
        nesenesewnwwnwnwsenewnwwnenenwenwsene
        swenwenwneeeswsweeseneeneneswe
        swswsewneswswwswswwsww
        nwnwnwnwnwnwnwnwsenenwnwnw
        nwnwewnwwnwnwnwnwnwswnwenwnwnw
        senwswnwwnesenwswneswnwwwsenwsenenee
        neswseswenenwwnwnwnenwwnwenwnwnenwne
        nenwnenenenenwnenenenesenwnenene
        neesenenewnenesenenewneneeneneenenene
        nwwnenwnwneenenenenenwnwnenenewseenene
        senenewsenwnwnwnwnenwnenenwnwnewnenwnwnwse
        ewnwwnwwwwsew
        swseswwwswswwswneswenewswwswswswswswsw
        sweneeeseeenwneseswseswseeneeswese
        nwwnenwnwnenwnwnwnwnenwnwnwsenwnwswnwneswe
        eeeeeeeweneeweeseeneeee
        swenweseswenwnwswswnwnenese
        nwwnwneeswsenwwsenwnenwenwwnwwnwnw
        nenewseswwnenenewneseneesenenwnenenene
        nenenwnenenenwnesenwnesenenenene
        nenenenenewneneneneneseneneneswnenwnewse
        wseseseseswsesesesesenwswenwseesenwne
        eenesewneneseenenenesenewneeewne
        seseswwseeswnwseswseweswswswswneswse
        neeneneneneseswneneewneneneenenewene
        eesenweesweeesesee
        wseswwnwwwnwwnwneenenwnwswnwsenwnewe
        wwnwewswwweswwwswswwwsweww
        esweewweenwseswesweneeenwene
        eewneneneewneneeeneneneneenee
        seneseseseeseswsenwwseseseeseseseewse
        eeneeneesenewneneneee
        swwswenwswswswswswswsweswswswnwswswsw
        wnwswweewwwnwwswwwwenwwnw
        nweneseewwwwseenwenenwnwenwswwnw
        eeesweseeweeeenwseneeewesenw
        eenewneeeeseewseeeeeeee
        ewseswneeeseneeenewenene
        senwnwnwnenenwnenwnenwnwnwnwnw
        nwnwnwnenwnwswnwwnwnwnwswnwnwnwne
        nwnenwneswnwnewswnwseenenenwnenenenese
        nwswnwnwnwwenwesenwswwnwnweneseswese
        nwnwwwwsewweneswswwwsewnwnwenenw
        esenwesesesweseseswsenwseseseseseenwsesw
        eeeneeeseeseeeesewsweneewswe
        ewswsweeeneeenweeneeewneseneee
        seseseseseswsesesesesesenwsenwswswswsenw
        neenwwsweeneswsesesesesewwseneesw
        eseswweeseeneswnweeeeeseneese
        swswnwswswnwseswswswnwswsweswsweswneesw
        seseenwwnwneneweeseswseseswseswsenwse
        seneseswswswswswswswseseseseswsw
        eeewseseneseseseseseeeeseese
        enwnwnwnwswnwnwnwnwnwnwnwwnwwnw
        seseseswnwseseeseseeeeesese
        swneswseswswwswswwneswswswswwswswsw
        wwwsenwwwnwswnwwwswewnwwwnwe
        seseswneneseswswneseswswsenwsesesesesesew
        senwswswswsweeswnwse
        senwnwenwnwneswwnweswnwnenweeswnwnww
        neeswesewesewnwnwnewswseswseseswne
        nwwwnwwsewnenwnwwnwnwnwsewwnwwnw
        wwswnwwswneswwwwwwewwwwsww
        swwseseseeseswwnewseneneswseswsw
        eewsewseeseeneseseeneeseseeee
        swswseneseswwseseseswne
        swswnweswswswnwswswswswswswswswesw
        seneseswsewseseswseswseseseswsesene
        swsesesesesesewsesesewseeseseseenwse
        swswnweneeeeeneenweeswenweeese
        seeeewnweswneeeneeeeeenwneeee
        nenwnenenwnwswesesesesewneneswwwewse
        swswswnwnwsweswwwsweswwswswswswswswsw
        wnwenwnenwenenwseswsesewswnw
        neswswseswswseseswnwseswswswswswswswswswnw
        neeseesesewseswnwesweswnewneewnwe
        seseseewnweeseneseeseseseesesesese
        nwneneneeswneneweeneneneesesenwne
        neneneneneneseswnewneneseneneneneenewne
        wwnwwewnwnenwnewwswwnwsewwnw
        eeseeseswnenweseesenweeswesesesw
        wswsweswswwnwswswswsw
        eswseneneswseswswsenwswwwneswwwswse
        enwseseseseseeswsewesenewwse
        nenenewswseseenenenwneswwnewswneeneenw
        swwnwneseeesweswswseeneenwnweswne
        eeseeseenweesesesesese
        swwenwsweneswsesweswswswswswswswnwswswnw
        swwesenenewnwneneeswnwneeswnwswnenwe
        swswswsesesweswswswswnwswsw
        wnewwwsewswseeewnwwswwnewewnew
        neeseneseswenwseseseeswswsewsesenwenw
        swnenesewswswneswswwswswswwswswneseswsww
        seswneneeeneneneswwneseswnenwneswnenenw
        senweswwswswwwwwwwwnewwwswsew
        wnwwwwsenwwnewenwwwwnwwnwwew
        neneeseswswnenewseneneenenewnenwneneww
        senenwnwseseneswnwswnwwwewwswnwneeww
        sesesesesenwseseesesesesewswseseswese
        sesesesesewneswseswswswswseswswnwseswswe
        senwnwneneneenenenwwneswnwnenenenenwnw
        wnwnwwwnenwwwsenw
        seesewwweeseeeseeeseseeseee
        swswswswseswseswsweseseswswwswewswnwne
        eseeeeswseseenwseesesesweesesenw
        nwnwswnwenwwewnwnwnewnwnwnenwnwswnw
        seswnenwwenwnwnwnwnwnwnwnwnwnwnenwnwnw
        wwseswwwswewswnwwnewneswwenwnw
        seseswseeswswsenwsesewswneswseswseseswsw
        swswswswnwswseseseneswseseswswswswnwneswsw
        wwewnwwwsweseswwwwwwwwwnese
        swswnwneweswswswswswneswswewswsw
        wnenwewewwwnwnwnewnwsewseeswwne
        neswsenenweenwweseeeswseeewsew
        esesenwswsesesesenenwsewsesenwsesesee
        swsesenwwnweeswnesewwseenw
        eeneeneneenwneneeesesweneeeeew
        nweseneneneswenesewnwwswswnenwnwsese
        swnwswewwwneseswwsweswwswswswesw
        swseseswseswseseswseneswswneswwwsesese
        wwwwswnwnwsewwsenwnwnwswnwenwnwnwnenw
        wsweswswwnewsweneswswswswwswswwnw
        neeswnweneneneenwnwewwswnwnwwnwnenw
        nwenwnwnwnwnenwnenewse
        swewwwneeswneneseneseneneeenenwneenw
        eenenenesenwneeseneneneswsenenewnenwne
        wsweseeswneeweneneneeneeeeneee
        nwnwnwsenenwnwnwnwnwswnwnwnw
        neneenwnwneswwnenwseswesenwenewnwne
        eswneswswwswneswswswswnwsw
        nweeswsweseeeeeeseenweeeeenwe
        weneswsweswswswswseswwswswseswswsww
        senwswswswswseswsewweseswsenwswswnenesw
        sesenwsesesesesesesesese
        swnenweenwneeeeeseenwwswswe
        neenenwsenwweswnenwnwnenwnenwwnenenw
        sewseseswseneneseeseswsenwwnesesesene
        nwwswwnwwwwswwwsewwewnewew
        nesewwwseseewnwseeswwsesenesenene
        weeneeseseeesweewe
        swswswswseseseswseneeneseseswnwsw
        neseseseweseseseseenwsesesesesesesese
        seswswsweswswswswswwsweswswswswswnwsw
        wwwwsewsewnewwwwnwwwwww
        nwneswnwnenwnenwnwnenwnwnenenenw
        seenesesewsweeneneneneenenenwnewe
        nwwnwnwnwswwnwnwnwnwenwnwnw
        newseneseewnwnwseneneneneeneneneswnew
        neeeeseeeneswneswweneswnenewneene
        nwswswnwenenwswnwnenenenenwnenwnenenwnw
        eseeeeseeeseweeeeeeenw
        swsenwwswnweneswewsenenesesenweese
        seeeeseseewswsenweseneseeseeesenw
        neseswwseseneweseseseseswsesewsesenesese
        newesenwnwewnwnwnwwewseseswwwwnw
        seswneswneenwswneswswwenwseswwswswwsw
        nenwnwnenenwnesenwne
        sewseeeseeeseee
        esweweenweeeeseweeneneeesw
        ewswnenwswnwsenwwwwseewewseswww
        wwneneneseseswsesenwe
        wswneswwswewewsenwenwnewweseswsw
        wswwwwwnwnwnewww
        senwnwnwnwnwnwwnwnw
        senenenwnenwnenwnewnwnwnenwsenwnenenenwsw
        nenwsenwnwnwnwswnwsenwnwnenwnwnwnwseenwesw
        seseswwneseeswnwswswneeswsewswswnew
        swwewwwseneenewswwswswsenwswswsw
        nwnwsenweeneneneeeneweeswseneswne
        swswnewwswswenweseswewsweswswewsw
        wseswnwswseswnwnwswswnenweeswswneswse
        wnenwswnwnenwenenwnwnwnwnwnwnesenenwnwne
        eswsweswneweswenenwswnwwnwnwsenwnese
        neeeneeeeneesweeneeweenee
        swwneswesewswwswwnwneswseneswsenwsw
        ewswswwswwwwwswwswseswwnewswe
        nwsewsesweewseseseseseswseswsesesesese
        enwnwnwswnenwwnwewnwnewswnwenwseswsw
        wwsewwneswwwsenwwnewsewswnwnew
        eeeneeswswnwneeseneseeneeewene
        seseswwseeswsesewneeseseswsesenesenwsesw
        wswswswswswwseswnwswswneswsw
        wneswswwnwnwenewwwwwwnwswnwesenwne
        swnwnwnwnwnwnwenwnw
        swswnenenenenenenenenewwseneneneneenenene
        nwwwwnwwenwnwnwwwwwweswwnww
        swnwnwnwnwnwnwnwnwnwnwnwnwne
        seseseseeseeesewsene
        enwnwwnwnwnwnwnwnwnwnenwnenwnwnw
        wswnewwwwswwweswwwwwswwew
        sesewswswseswseseneswse
        sesenweweeeseeneeeseesesweee
        swswswswswswswswwweswswesenwnweenwse
        swswswnwswwswseswseenenesweswwswwsw
        seseeswnweseseswswsenwnwnwseesenwnwww
        wnwnwwwwwsenewwnwwnwnwsene
        swesenwseseswnwseseswnwswsweseseseswswsese
        senwwenwnwnenewnwsenenwnenweewswse
        eeenenwenesenewneweneeeeesenwe
        senwwneseseseseseseseseesesesesesesese
        ewnwswnwswenwswnesewseseneseswswsesw
        nesenenweeeeeswwnenwnesweeneenene
        neeswwewnweenwewneswsesenwwseee
        nesenwseesenwnwswnwsenwnwswenwnwnenwnenw
        senenenwnenenewsewnenwnenwnwnwneenewsene
        wwwswswwnewwwswswswsenewwwww
        neseseenweenesewswsewwseswseseseesw
        seseseseseneseseswsesenesesesesesenewwse
        swseswswswswswswwseeswseswswsw
        swnewnewsesewneswwswwnw
        seesesesenwseseswseseseseneeseswseee
        neneneneneneseneenwwnenenenenenenwwne
        seseewenwenwseeseseeeeseeneeneesw
        neswsweeseeseneeesesenwswewnwe
        seneswnwwnwwwwnwnenesewenwnwwwnwww
        newnwnewswwwwwwwswwswwswwwsew
        swseswwnwwwwwswsewswswenwwsw
        wnenwnwewsenwnewsewwswnenwsewwswnw
        nenwneswnwnesenenwnewsenenwneswnenwnwenw
        wwswnwnwnwwwwwwnew
        swswswnesesenwseswsewweneneswnesewse
        swwsewwwwwwswwneswnenwwwnewsw
        nwnwsenenenenenenenwnenenesenenwnwenwsw
        sesesenweswswseseseswswswswswnwseswese
        wsewwnwwwewnewwwsewwwwww
        swseswswwnwesenwswswsenee
        sesweseesewsewswswseseseswneseseswsesw
        nwsenenenwnwnwwnwnwnwnwenwnenwnwnenwnwsw
        nwwnwsesewwewneseswwwnw
        eeseseseeseseeswenwseeeseeeenwsw
        nwwnewwwsewwsew
        wwnewwwwswswwneswswwwneswwwse
        wswswswswswswswnwswwswswseneswneseswwsw
        nenwnwwseeweswswswswnwnwwewneeswswse
        nwnwnwenwsenweswwnenwnwsewnwwwnwnwnwne
        eseswwnwneenwsewswsenweswwneswnenw
        wswswswnwswneswswwswsenew
        swwsesewnwwnwwswswwswwwswsww
        wwswesewswswswswnenwswswswnewwwsw
        swwwswseswswwswneswswnesweswneswswswsw
        swnwnwswnwnenwwwwswnwwnwnwenwnwne
        seeseseesesesenwswsesenwswsesesesesese
        seeeseseseseseeseesenweswwseewsese
        enenenenenewneseseswnenwswnenenenwnenene
        swwweswesweswnwnweneenweseswene
        senenwnenwsweweenweseneswseneswwnw
        nesewneneswwneseneesenenenwnenwnwswse
        swnesesewnenwwwnwnwenesewwweseew
        nenenesesenwnwnenewnenwsenenwnwnwnenwne
        nwseseeeseesenwewseeeseseseenwsw
    """.trimIndent()
}