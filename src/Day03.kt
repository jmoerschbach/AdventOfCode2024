import kotlin.math.pow

fun main() {


    fun part1(input: List<String>): Long {
        var sum = 0L
        input.forEach { inputString ->

            val firstFactor = mutableListOf<Int>()
            val secondFactor = mutableListOf<Int>()


            var token = ""
            for (i in inputString.indices) {
                val currentChar = inputString[i]
                if (currentChar == 'm' && token == "") {
                    token = "m"
                } else if (currentChar == 'u' && token == "m") {
                    token = "u"
                } else if (currentChar == 'l' && token == "u") {
                    token = "l"
                } else if (currentChar == '(' && token == "l") {
                    token = "("
                } else if (currentChar.isDigit() && token == "(") {
                    firstFactor.add(currentChar.toString().toInt())
                } else if (currentChar == ',' && firstFactor.isNotEmpty()) {
                    token = ","
                } else if (currentChar.isDigit() && token == ",") {
                    secondFactor.add(currentChar.toString().toInt())
                } else if (currentChar == ')' && secondFactor.isNotEmpty()) {
                    token = ""
                    var ff = 0
                    for (j in 0 until firstFactor.size) {
                        ff += firstFactor[j] * 10.0.pow(firstFactor.size - j - 1).toInt()
                    }
                    var sf = 0
                    for (j in 0 until secondFactor.size) {
                        sf += secondFactor[j] * 10.0.pow(secondFactor.size - j - 1).toInt()
                    }
                    sum += ff * sf
                    firstFactor.clear()
                    secondFactor.clear()
                } else {
                    token = ""
                    firstFactor.clear()
                    secondFactor.clear()
                }

            }
        }
        return sum
    }


    fun part2(input: List<String>): Long {
        var sum = 0L
        var inputString = ""
        input.forEach { inputS ->
            inputString += inputS
        }

        val firstFactor = mutableListOf<Int>()
        val secondFactor = mutableListOf<Int>()

        var enabled = true
        var token = ""
        var doDontToken = ""
        for (i in inputString.indices) {
            val currentChar = inputString[i]
            if (currentChar == 'm' && token == "") {
                token = "m"
            } else if (currentChar == 'u' && token == "m") {
                token += "u"
            } else if (currentChar == 'l' && token == "mu") {
                token += "l"
            } else if (currentChar == '(' && token == "mul") {
                token += "("
            } else if (currentChar.isDigit() && token == "mul(") {
                firstFactor.add(currentChar.toString().toInt())
            } else if (currentChar == ',' && firstFactor.isNotEmpty() && token == "mul(") {
                token += ","
            } else if (currentChar.isDigit() && token == "mul(,") {
                secondFactor.add(currentChar.toString().toInt())
            } else if (currentChar == ')' && secondFactor.isNotEmpty() && token == "mul(,") {
                token = ""
                if (enabled) {
                    var ff = 0
                    for (j in 0 until firstFactor.size) {
                        ff += firstFactor[j] * 10.0.pow(firstFactor.size - j - 1).toInt()
                    }
                    var sf = 0
                    for (j in 0 until secondFactor.size) {
                        sf += secondFactor[j] * 10.0.pow(secondFactor.size - j - 1).toInt()
                    }
                    sum += ff * sf
                    firstFactor.clear()
                    secondFactor.clear()
                }
            } else if (currentChar == 'd' && doDontToken == "") {
                doDontToken = "d"
            } else if (currentChar == 'o' && doDontToken == "d") {
                doDontToken += "o"
            } else if (currentChar == '(' && doDontToken == "do") {
                doDontToken += "("
            } else if (currentChar == ')' && doDontToken == "do(") {
                token = ""
                doDontToken = ""
                enabled = true
            } else if (currentChar == 'n' && doDontToken == "do") {
                doDontToken += "n"
            } else if (currentChar == '\'' && doDontToken == "don") {
                doDontToken += "'"
            } else if (currentChar == 't' && doDontToken == "don'") {
                doDontToken += "t"
            } else if (currentChar == '(' && doDontToken == "don't") {
                doDontToken += "("
            } else if (currentChar == ')' && doDontToken == "don't(") {
                token = ""
                doDontToken = ""
                enabled = false
            } else {
                token = ""
                doDontToken = ""
                firstFactor.clear()
                secondFactor.clear()
            }

        }

        return sum
    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day03_test")
//part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part12(input).println()
    part2(input).println()
}
