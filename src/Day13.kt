data class Game(val offsetButtonA: PointLong, val offsetButtonB: PointLong, val price: PointLong) {
    fun findCheapestSolutionDumb(): Int? {
        for (a in 0..100) {
            for (b in 0..100) {
                val x = a * offsetButtonA.x + b * offsetButtonB.x
                val y = a * offsetButtonA.y + b * offsetButtonB.y
                if (x == price.x && y == price.y) {
                    return 3 * a + b
                }
            }
        }
        return null
    }

    fun findCheapestSolutionSmart(): Long? {

        //Cramer's rule
        val a =
            (price.x * offsetButtonB.y - price.y * offsetButtonB.x) / (offsetButtonA.x * offsetButtonB.y - offsetButtonA.y * offsetButtonB.x)
        val b =
            (offsetButtonA.x * price.y - offsetButtonA.y * price.x) / (offsetButtonA.x * offsetButtonB.y - offsetButtonA.y * offsetButtonB.x)
        return if (a * offsetButtonA.x + b * offsetButtonB.x == price.x && a * offsetButtonA.y + b * offsetButtonB.y == price.y) {
            3 * a + b
        } else {
            null
        }
    }
}

fun main() {

    fun parse(input: String, additionalDelimiter: String = "+", priceOffset: Long = 0): PointLong {
        val x = input.substringAfter("X$additionalDelimiter").substringBefore(",").toLong() + priceOffset
        val y = input.substringAfter("Y$additionalDelimiter").toLong() + priceOffset
        return PointLong(x, y)
    }


    fun part1(input: List<String>): Int {
        return input.chunked(4).map {
            val buttonA = parse(it[0])
            val buttonB = parse(it[1])
            val prizePosition = parse(it[2], "=")
            Game(buttonA, buttonB, prizePosition)
        }.sumOf { it.findCheapestSolutionDumb() ?: 0 }


    }


    fun part2(input: List<String>): Long {
        return input.chunked(4).map {
            val buttonA = parse(it[0])
            val buttonB = parse(it[1])
            val prizePosition = parse(it[2], "=", 10000000000000)
            Game(buttonA, buttonB, prizePosition)
        }.sumOf { it.findCheapestSolutionSmart() ?: 0 }
    }


// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day13_test")
//    part1(testInput).println()
//    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
