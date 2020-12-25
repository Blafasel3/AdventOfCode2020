package advent.of.code.day25

import org.junit.Test

class Day25Test {
    private val remainder = 20201227L

    @Test
    fun `part 1`() {
        val cardPublicKey = 11404017L
        val cardLoopSize = findLoopSize(cardPublicKey)
        val doorPublicKey = 13768789L
        val doorLoopSize = findLoopSize(doorPublicKey)

        println(transformPublicKeyToEncryptionKey(cardLoopSize, doorPublicKey))
        println(transformPublicKeyToEncryptionKey(doorLoopSize, cardPublicKey))
    }

    private fun findLoopSize(publicKey: Long, subjectNumber: Long = 7L): Int {
        var value = 1L
        var loopSize = 0
        while (value != publicKey) {
            loopSize++
            value = (value.times(subjectNumber)).rem(remainder)
        }
        return loopSize
    }

    private fun transformPublicKeyToEncryptionKey(otherDeviceLoopSize: Int, subjectNumber: Long): Long {
        var value = 1L
        for (i in 0 until otherDeviceLoopSize) {
            value = (value.times(subjectNumber)).rem(remainder)
        }
        return value
    }
}