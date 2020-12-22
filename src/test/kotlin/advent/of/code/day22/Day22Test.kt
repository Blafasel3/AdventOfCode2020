package advent.of.code.day22

import org.junit.Test

class Day22Test {

    @Test
    fun `part 1`() {
        val blocks = puzzleInput.split(Regex("\\s+\\n"))
        val player1Deck = getStartingDeck(blocks.first())
        val player2Deck = getStartingDeck(blocks.last())

        val (winner, score) = playCombat(player1Deck, player2Deck)

        println("Winner: $winner - Score: $score")
    }

    @Test
    fun `part 2 - Recursive Combat`() {
        val blocks = puzzleInput.split(Regex("\\s+\\n"))
        val player1Deck = getStartingDeck(blocks.first())
        val player2Deck = getStartingDeck(blocks.last())

        val previousRounds = mutableSetOf<PreviousRound>()

        val playRecursiveCombat = playRecursiveCombat(player1Deck, player2Deck)
        previousRounds.addAll(playRecursiveCombat.second)
        val score = if (playRecursiveCombat.first == 1) {
            calculateScore(player1Deck)
        } else {
            calculateScore(player2Deck)
        }

        println("== Post-game results ==")
        println("Winner: Player ${playRecursiveCombat.first} - Score: $score")
        println("Player 1 deck: $player1Deck")
        println("Player 2 deck: $player2Deck")
    }

    private fun playRecursiveCombat(
        player1Deck: MutableList<Int>,
        player2Deck: MutableList<Int>
    ): Pair<Int, MutableSet<PreviousRound>> {
        var round = 0
        val previousRounds = mutableSetOf<PreviousRound>()
        var currentRound = PreviousRound(player1Deck, player2Deck)
        while (player1Deck.isNotEmpty() && player2Deck.isNotEmpty()) {
            round++
            if (previousRounds.contains(currentRound)) {
                return Pair(1, previousRounds)
            }
            previousRounds.add(currentRound)
            val player1Card = player1Deck.removeFirst()
            val player2Card = player2Deck.removeFirst()
            val player1HasEnoughCards = hasEnoughCards(player1Deck, player1Card)
            val player2HasEnoughCards = hasEnoughCards(player2Deck, player2Card)
            if (player1HasEnoughCards && player2HasEnoughCards) {
                // play a round of recursive combat with a copy of the subdecks
                val recursiveCombatResult = playRecursiveCombat(
                    player1Deck.slice(0 until player1Card).toMutableList(),
                    player2Deck.slice(0 until player2Card).toMutableList()
                )
                previousRounds.addAll(recursiveCombatResult.second)
                if (recursiveCombatResult.first == 1) {
                    player1Deck.add(player1Card)
                    player1Deck.add(player2Card)
                } else {
                    player2Deck.add(player2Card)
                    player2Deck.add(player1Card)
                }
            } else {
                if (player1Card > player2Card) {
                    player1Deck.add(player1Card)
                    player1Deck.add(player2Card)
                } else {
                    player2Deck.add(player2Card)
                    player2Deck.add(player1Card)
                }
            }
            currentRound = PreviousRound(player1Deck, player2Deck)
        }
        if (player2Deck.isEmpty()) {
            return Pair(1, previousRounds)
        }
        return Pair(2, previousRounds)
    }

    private fun playCombat(
        player1Deck: MutableList<Int>,
        player2Deck: MutableList<Int>
    ): Pair<Int, Int> {
        var round = 0
        while (player1Deck.isNotEmpty() && player2Deck.isNotEmpty()) {
            round++
            val player1Card = player1Deck.removeFirst()
            val player2Card = player2Deck.removeFirst()
            if (player1Card > player2Card) {
                player1Deck.add(player1Card)
                player1Deck.add(player2Card)
            } else {
                player2Deck.add(player2Card)
                player2Deck.add(player1Card)
            }
        }
        if (player2Deck.isEmpty()) {
            return Pair(1, calculateScore(player1Deck))
        }
        return Pair(2, calculateScore(player2Deck))
    }

    private fun calculateScore(deck: MutableList<Int>): Int =
        deck.reversed().reduceIndexed { index, acc, card -> acc + card * (index + 1) }


    private fun hasEnoughCards(deck: List<Int>, card: Int): Boolean = deck.count() >= card

    private fun getStartingDeck(playerDeck: String) =
        playerDeck.split("\\n".toRegex()).drop(1).map { it.toInt() }.toMutableList()


    private val puzzleInput = """
        Player 1:
        41
        26
        29
        11
        50
        38
        42
        20
        13
        9
        40
        43
        10
        24
        35
        30
        23
        15
        31
        48
        27
        44
        16
        12
        14

        Player 2:
        18
        6
        32
        37
        25
        21
        33
        28
        7
        8
        45
        46
        49
        5
        19
        2
        39
        4
        17
        3
        22
        1
        34
        36
        47
    """.trimIndent()
}