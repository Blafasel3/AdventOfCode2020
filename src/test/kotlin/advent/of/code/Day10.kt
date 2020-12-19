package advent.of.code

import org.junit.Test
import kotlin.test.fail

class Day10 {

    @Test
    fun `part 1`() {
//        val testInput = setOf(
//            16,
//            10,
//            15,
//            5,
//            1,
//            11,
//            7,
//            19,
//            6,
//            12,
//            4
//        )
//        val testInput = setOf(
//            28,
//            33,
//            18,
//            42,
//            31,
//            14,
//            46,
//            20,
//            48,
//            47,
//            24,
//            23,
//            49,
//            45,
//            19,
//            38,
//            39,
//            11,
//            1,
//            32,
//            25,
//            35,
//            8,
//            17,
//            7,
//            9,
//            4,
//            2,
//            34,
//            10,
//            3
//        )
        val testInput = puzzleInput
        var currentJolt = 0L
        var numberOf1JoltDiffs = 0
        var numberOf3JoltDiffs = 0

        for (i in testInput.indices) {
            val nextJolt = testInput.filter { it > currentJolt && it <= currentJolt + 3 }
                .minOrNull() ?: throw NoSuchElementException("Missing follow up for $currentJolt")
            when (nextJolt - currentJolt) {
                1L -> numberOf1JoltDiffs++
                3L -> numberOf3JoltDiffs++
            }
            currentJolt = nextJolt
        }

        val max = testInput.maxOrNull()
        if (max == null) {
            fail()
            return
        }
        println(currentJolt)
        val deviceJolt = max + 3
        println("$deviceJolt - $currentJolt = ${deviceJolt - currentJolt}")
        numberOf3JoltDiffs++ // last one is per default
        println("numberOf1JoltDiffs: $numberOf1JoltDiffs")
        println("numberOf3JoltDiffs: $numberOf3JoltDiffs")
        println("product: ${numberOf1JoltDiffs * numberOf3JoltDiffs}")
        if (deviceJolt - currentJolt > 3) {
            fail("diff should be less than 3! $deviceJolt $currentJolt")
        }
    }

    @Test
    fun `part 2 permutations`() {
        val testInput = setOf(
            1L,
            4L,
            5L,
            6L,
            7L,
            10L,
            11L,
            12L,
            15L,
            16L,
            19L
        )

//        val testInput = setOf(
//            28L,
//            33L,
//            18L,
//            42L,
//            31L,
//            14L,
//            46L,
//            20L,
//            48L,
//            47L,
//            24L,
//            23L,
//            49L,
//            45L,
//            19L,
//            38L,
//            39L,
//            11L,
//            1L,
//            32L,
//            25L,
//            35L,
//            8L,
//            17L,
//            7L,
//            9L,
//            4L,
//            2L,
//            34L,
//            10L,
//            3L
//        )
//        val testInput = puzzleInput
        var currentJolt = 0L
        val adapters = mutableListOf<Long>(0)
        for (i in testInput.indices) {
            val nextJolt = testInput.filter { it > currentJolt && it <= currentJolt + 3 }
                .minOrNull() ?: throw NoSuchElementException("Missing follow up for $currentJolt")
            currentJolt = nextJolt
            adapters.add(currentJolt)
        }
        val max = testInput.maxOrNull()
        if (max == null) {
            fail()
            return
        }
        val deviceJolt = max + 3
        adapters.add(deviceJolt)
        println(adapters.reversed())
        val joltMap = mutableMapOf(
            adapters.last() to 0L, // device jolts (max jolt value of adapters + 3), fixed
            adapters[adapters.size - 2] to 1L // max jolt value of adapters, fixed
        ) // basically a "Tribunacci series" where values in between are missing (considered zero)
        for (index in adapters.size - 3 downTo 0) { // traverse backwards
            var permutations =
                joltMap[adapters[index + 1]] ?: 0 // Next adapter has to fit since this is a valid solution
            if (index + 3 < adapters.size && adapters[index + 3] - adapters[index] <= 3) {
                permutations += joltMap[adapters[index + 3]] ?: 0
            }
            if (index + 2 < adapters.size && adapters[index + 2] - adapters[index] <= 3) {
                permutations += joltMap[adapters[index + 2]] ?: 0
            }
            println("${adapters[index]}: $joltMap")
            joltMap[adapters[index]] = permutations
        }
        println(joltMap[0])
    }

    private val puzzleInput = setOf(
        115L,
        134L,
        121L,
        184L,
        78L,
        84L,
        77L,
        159L,
        133L,
        90L,
        71L,
        185L,
        152L,
        165L,
        39L,
        64L,
        85L,
        50L,
        20L,
        75L,
        2L,
        120L,
        137L,
        164L,
        101L,
        56L,
        153L,
        63L,
        70L,
        10L,
        72L,
        37L,
        86L,
        27L,
        166L,
        186L,
        154L,
        131L,
        1L,
        122L,
        95L,
        14L,
        119L,
        3L,
        99L,
        172L,
        111L,
        142L,
        26L,
        82L,
        8L,
        31L,
        53L,
        28L,
        139L,
        110L,
        138L,
        175L,
        108L,
        145L,
        58L,
        76L,
        7L,
        23L,
        83L,
        49L,
        132L,
        57L,
        40L,
        48L,
        102L,
        11L,
        105L,
        146L,
        149L,
        66L,
        38L,
        155L,
        109L,
        128L,
        181L,
        43L,
        44L,
        94L,
        4L,
        169L,
        89L,
        96L,
        60L,
        69L,
        9L,
        163L,
        116L,
        45L,
        59L,
        15L,
        178L,
        34L,
        114L,
        17L,
        16L,
        79L,
        91L,
        100L,
        162L,
        125L,
        156L,
        65L
    )
}