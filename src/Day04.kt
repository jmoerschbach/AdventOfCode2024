import kotlin.math.pow

fun main() {

    fun addPadding(input: List<String>): List<List<String>> {
        val paddedRowLength = input[0].length + 2 * 3
        val paddedInput = input.map { "...$it..." }.toMutableList()
        for (i in 0 until 3) {
            paddedInput.add(0, ".".repeat(paddedRowLength))
            paddedInput.add(".".repeat(paddedRowLength))
        }
        return paddedInput.map { it.map(Char::toString) }

    }


    fun part1(input: List<String>): Int {
        val array = addPadding(input)

        var count = 0
        for (row in 0 until array.size) {
            for (col in 0 until array[row].size) {
                val currentChar = array[row][col]
                if (currentChar == "X") {
                    if (array[row][col - 1] == "M" && array[row][col - 2] == "A" && array[row][col - 3] == "S") {
                        count++
                    }
                    if (array[row][col + 1] == "M" && array[row][col + 2] == "A" && array[row][col + 3] == "S") {
                        count++
                    }
                    if (array[row - 1][col] == "M" && array[row - 2][col] == "A" && array[row - 3][col] == "S") {
                        count++
                    }
                    if (array[row + 1][col] == "M" && array[row + 2][col] == "A" && array[row + 3][col] == "S") {
                        count++
                    }
                    if (array[row + 1][col + 1] == "M" && array[row + 2][col + 2] == "A" && array[row + 3][col + 3] == "S") {
                        count++
                    }
                    if (array[row - 1][col - 1] == "M" && array[row - 2][col - 2] == "A" && array[row - 3][col - 3] == "S") {
                        count++
                    }
                    if (array[row + 1][col - 1] == "M" && array[row + 2][col - 2] == "A" && array[row + 3][col - 3] == "S") {
                        count++
                    }
                    if (array[row - 1][col + 1] == "M" && array[row - 2][col + 2] == "A" && array[row - 3][col + 3] == "S") {
                        count++
                    }

                }
            }
        }
        return count
    }


    fun part2(input: List<String>): Int {
        val array = addPadding(input)

        var count = 0
        for (row in 0 until array.size) {
            for (col in 0 until array[row].size) {
                val currentChar = array[row][col]
                if(currentChar == "A") {
                    if(array[row-1][col-1] == "M" && array[row+1][col+1] == "S" && array[row+1][col-1] == "S" && array[row-1][col+1] == "M") {
                        count++
                    }
                    if(array[row-1][col-1] == "M" && array[row+1][col+1] == "S" && array[row+1][col-1] == "M" && array[row-1][col+1] == "S") {
                        count++
                    }
                    if(array[row-1][col-1] == "S" && array[row+1][col+1] == "M" && array[row+1][col-1] == "M" && array[row-1][col+1] == "S") {
                        count++
                    }
                    if(array[row-1][col-1] == "S" && array[row+1][col+1] == "M" && array[row+1][col-1] == "S" && array[row-1][col+1] == "M") {
                        count++
                    }
                }
            }
        }
        return count
    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day04_test")
    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
//    part1(input).println()
    part2(input).println()
}
