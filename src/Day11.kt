data class Stone(val value: Long) {
    fun blink(): List<Stone> {
        if (value == 0L) return listOf(Stone(1))

        //dumb way to split the number in half
        val numberAsString = value.toString()
        if (numberAsString.length % 2 == 0) {
            val left = numberAsString.substring(0, numberAsString.length / 2).toLong()
            val right = numberAsString.substring(numberAsString.length / 2).toLong()
            return listOf(Stone(left), Stone(right))
        }

        return listOf(Stone(value * 2024))
    }
}

data class ExtendedStone(val value: Long, val amount: Long = 1) {
    fun blink(): List<ExtendedStone> {
        if (value == 0L) return listOf(ExtendedStone(1, this.amount))

        //dumb way to split the number in half
        val numberAsString = value.toString()
        if (numberAsString.length % 2 == 0) {
            val left = numberAsString.substring(0, numberAsString.length / 2).toLong()
            val right = numberAsString.substring(numberAsString.length / 2).toLong()
            return listOf(ExtendedStone(left, this.amount), ExtendedStone(right, this.amount))
        }

        return listOf(ExtendedStone(value * 2024, this.amount))
    }
}

fun Long.toStone(): Stone {
    return Stone(this)
}

fun Long.toExtendedStone(amount: Long = 1): ExtendedStone {
    return ExtendedStone(this, amount)
}

fun main() {


    fun part1(input: List<String>): Int {
        var result = input[0].split(" ").map { it.toLong().toStone() }
        repeat(25) { result = result.flatMap { it.blink() } }

        return result.size
    }

    fun part2(input: List<String>): Long {
        var result = input[0].split(" ").map { it.toLong().toExtendedStone() }
        repeat(75) {
            val intermediate = result.flatMap { it.blink() }
            // group by stone value (key) and then sum the amounts of the all the stones with same stone value
            // producing a new single ExtendedStone for each group aka key aka stone value
            result = intermediate.groupBy { it.value }.map { it.key.toExtendedStone(it.value.sumOf { it.amount }) }
        }
        return result.sumOf { it.amount }
    }


// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day11_test")
//    part1(testInput).println()
//    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
