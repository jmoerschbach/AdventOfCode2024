data class Equation(val testValue: Long, val numbers: List<Long>)

fun String.toEquation(): Equation {
    val targetResult = this.substringBefore(":").toLong()
    val numbers = this.substringAfter(": ").split(" ").map { it.toLong() }
    return Equation(targetResult, numbers)
}


fun main() {
    fun canBeCalibrated(targetResult: Long, numbers: List<Long>, pos: Int, currentTotal: Long): Boolean {
        if (pos >= numbers.size) {
            return currentTotal == targetResult
        }
        if (currentTotal > targetResult) {
            return false
        }
        return canBeCalibrated(targetResult, numbers, pos + 1, currentTotal + numbers[pos]) ||
                canBeCalibrated(targetResult, numbers, pos + 1, currentTotal * numbers[pos])
    }

    fun part1(input: List<String>): Long {
        return input.map(String::toEquation).filter {
            canBeCalibrated(it.testValue, it.numbers, 1, it.numbers[0])
        }.sumOf(Equation::testValue).toLong()
    }

    fun canBeCalibratedTernary(targetResult: Long, numbers: List<Long>, pos: Int, currentTotal: Long): Boolean {
        if (pos >= numbers.size) {
            return currentTotal == targetResult
        }
        if (currentTotal > targetResult) {
            return false
        }
        val concatenated = (currentTotal.toString() + numbers[pos].toString()).toLong()
        return canBeCalibratedTernary(targetResult, numbers, pos + 1, currentTotal + numbers[pos]) ||
                canBeCalibratedTernary(targetResult, numbers, pos + 1, currentTotal * numbers[pos]) ||
                canBeCalibratedTernary(targetResult, numbers, pos + 1, concatenated)
    }

    fun part2(input: List<String>): Long {
        return input.map(String::toEquation).filter {
            canBeCalibratedTernary(it.testValue, it.numbers, 1, it.numbers[0])
        }.sumOf(Equation::testValue).toLong()
    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day07_test")
    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part2(input).println()
//    part2(input).println()
}
