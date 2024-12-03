import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val listLeft = mutableListOf<Int>()
        val listRight = mutableListOf<Int>()
        input.forEach { pair ->
            val (a, b) = pair.split("   ").map(String::toInt)
            listLeft.add(a)
            listRight.add(b)
        }
        listLeft.sort()
        listRight.sort()

        var totalDistance = 0
        for (i in listLeft.indices) {
            totalDistance += abs(listLeft[i] - listRight[i])
        }
        return totalDistance
    }

    fun part2(input: List<String>): Int {
        val map = mutableMapOf<Int, Int>()

        val listLeft = mutableListOf<Int>()
        val listRight = mutableListOf<Int>()
        input.forEach { pair ->
            val (a, b) = pair.split("   ").map(String::toInt)
            listLeft.add(a)
            listRight.add(b)
        }
        listRight.forEach { rightValue ->
            map.compute(rightValue) { _, v -> v?.plus(1) ?: 1 }
        }
        var score = 0
        listLeft.forEach { leftValue ->
            score += map.getOrDefault(leftValue, 0) * leftValue
        }
        return score
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day02_test.txt` file:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
