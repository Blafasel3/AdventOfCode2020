package org.example

import org.junit.Test

class Day15 {

    //    If that was the first time the number has been spoken, the current player says 0.
//    Otherwise, the number had been spoken before; the current player announces
//    how many turns apart the number is from when it was previously spoken.
    @Test
    fun `part 1`() {
        val testInput = "0,3,6"
        val spokenNumbers = parseToNumberList(testInput)
        for (i in spokenNumbers.size until 10) {
            val lastNumberSpoken = spokenNumbers[i - 1]
            val previouslySpokenNumbers = spokenNumbers.subList(0, i - 1)
            if (previouslySpokenNumbers.contains(lastNumberSpoken)) {
                spokenNumbers.add(i - 1 - previouslySpokenNumbers.lastIndexOf(lastNumberSpoken))
            } else {
                spokenNumbers.add(0)
            }
        }
        println(spokenNumbers)
    }

    @Test
    fun `part 2`() {
        val spokenNumbers = parseToNumberList(puzzleInput)
        val indexByNumber = mutableMapOf<Int, Int>()
        spokenNumbers.forEachIndexed { index, number -> indexByNumber[number] = index }
        for (i in spokenNumbers.size until 30000000) {
            val previousIndex = i - 1
            val lastNumberSpoken = spokenNumbers[previousIndex]
            val previouslySpokenNumberIndex = indexByNumber.getOrDefault(lastNumberSpoken, defaultValue = -1)
            indexByNumber[lastNumberSpoken] = i - 1
            val newNumber = if (previouslySpokenNumberIndex == -1) {
                0
            } else {
                i - 1 - previouslySpokenNumberIndex
            }
            spokenNumbers.add(newNumber)
        }
        println(spokenNumbers.last())
    }

    private fun parseToNumberList(testInput: String) = testInput.split(",").map { it.toInt() }.toMutableList()

    private val puzzleInput = "6,19,0,5,7,13,1"
}