fun main() {

    fun Int.addToList(content: Int, list: MutableList<Int>) {
        repeat(this) {
            list.add(content)
        }
    }

    fun List<Int>.indexOfFirst(startIndex: Int = 0, predicate: (Int) -> Boolean): Int {
        for (i in startIndex until this.size) {
            if (predicate(this[i])) {
                return i
            }
        }
        return -1
    }

    fun List<Int>.indexOfFirstChunk(chunkLength: Int, endIndex: Int = this.size, predicate: (Int) -> Boolean): Int {
        for (i in 0 until endIndex) {
            if (i + chunkLength <= this.size && this.subList(i, i + chunkLength).all(predicate)) {
                return i
            }
        }
        return -1
    }

    fun calculateChecksum(list: List<Int>): Long {
        return list
            .withIndex()
            .filter { it.value > -1 }
            .sumOf { it.index * it.value.toLong() }
    }


    fun expandToDisk(inputLine: String): MutableList<Int> {
        var content = 0
        val expanded: MutableList<Int> = mutableListOf()
        inputLine.forEachIndexed { pos, c ->
            val c = c.toString().toInt()
            if (pos % 2 != 0) {
                c.addToList(-1, expanded)
            } else {
                c.addToList(content, expanded)
                content++
            }
        }
        return expanded
    }

    fun part1(input: List<String>): Long {

        val deFragmented = expandToDisk(input[0])

        var firstEmptySpaceIndex = deFragmented.indexOfFirst { it == -1 }

        for (i in deFragmented.size - 1 downTo 0) {
            if (firstEmptySpaceIndex >= i) {
                break
            }
            if (deFragmented[i] == -1) {
                continue
            }
            val temp = deFragmented[i]
            deFragmented[i] = deFragmented[firstEmptySpaceIndex]
            deFragmented[firstEmptySpaceIndex] = temp
            firstEmptySpaceIndex = deFragmented.indexOfFirst(startIndex = firstEmptySpaceIndex) { it == -1 }
//            println(list2)
        }
//        println(list2)
        return calculateChecksum(deFragmented)
    }


    fun part2(input: List<String>): Long {
        val deFragmented = expandToDisk(input[0])
        var i = deFragmented.size - 1
        while (i >= 0) {
            if (deFragmented[i] == -1) {
                i--
                continue
            }
            val currentChunkContent = deFragmented[i]
            var currentChunkLength = 0
            var currentChunkIndex = i

            while (currentChunkIndex >= 0 && deFragmented[currentChunkIndex] == currentChunkContent) {
                currentChunkIndex--
                currentChunkLength++
            }

            val firstEmptySpaceIndex =
                deFragmented.indexOfFirstChunk(
                    endIndex = currentChunkIndex + 1,
                    chunkLength = currentChunkLength
                ) { it == -1 }
//            println("currentChunkContent: $currentChunkContent, currentChunkLength: $currentChunkLength, currentChunkStartIndex: ${currentChunkIndex + 1}, currentChunkEndIndex: $i, firstEmptySpaceIndex: $firstEmptySpaceIndex")

            if (firstEmptySpaceIndex != -1) {

                for (j in currentChunkIndex + 1 until currentChunkIndex + 1 + currentChunkLength) {
                    deFragmented[j] = -1
                }
                for (j in firstEmptySpaceIndex until firstEmptySpaceIndex + currentChunkLength) {
                    deFragmented[j] = currentChunkContent
                }

            }
            i = currentChunkIndex
//            println(deFragmented)
        }
        return calculateChecksum(deFragmented)
    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day09_test")
//    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
