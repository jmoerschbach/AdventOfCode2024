import kotlin.math.pow

class StrangeDevice(var registerA: Int, var registerB: Int, var registerC: Int, val program: List<Int>) {
    var instructionPointer = 0

    fun decompileOperand(operand: Int): Int {
        return when (operand) {
            4 -> registerA
            5 -> registerB
            6 -> registerC
            else -> operand
        }
    }

    fun adv(operand: Int) {
        val denominator = 2.0.pow(decompileOperand(operand).toDouble())
        registerA = registerA / denominator.toInt()
    }

    fun bxl(operand: Int) {
        registerB = registerB xor operand
    }

    fun bst(operand: Int) {
        registerB = decompileOperand(operand) % 8
    }

    fun jnz(operand: Int) {
        instructionPointer = if (registerA != 0) {
            operand
        } else {
            instructionPointer + 2
        }
    }

    fun bxc(operand: Int) {
        registerB = registerB xor registerC
    }

    fun out(operand: Int, output: MutableList<Int>) {
        output.add(decompileOperand(operand) % 8)
    }

    fun bdv(operand: Int) {
        val denominator = 2.0.pow(decompileOperand(operand).toDouble())
        registerB = registerA / denominator.toInt()
    }

    fun cdv(operand: Int) {
        val denominator = 2.0.pow(decompileOperand(operand).toDouble())
        registerC = registerA / denominator.toInt()
    }

    fun runProgram(): List<Int> {
        val accumulatedOutput = mutableListOf<Int>()
        while (instructionPointer < program.size) {
            val instruction = program[instructionPointer]
            val operand = program[instructionPointer + 1]
            when (instruction) {
                0 -> {
                    adv(operand)
                }

                1 -> {
                    bxl(operand)
                }

                2 -> {
                    bst(operand)
                }

                3 -> {
                    jnz(operand)
                }

                4 -> {
                    bxc(operand)
                }

                5 -> {
                    out(operand, accumulatedOutput)
                }

                6 -> {
                    bdv(operand)
                }

                7 -> {
                    cdv(operand)
                }

                else -> {
                    println("Unknown instruction: $instruction")
                }
            }
            if (instruction != 3) {
                instructionPointer += 2
            }
            if (accumulatedOutput.size > program.size) {
                break
            }
//            println("After instruction: $instructionPointer, RegisterA: $registerA, RegisterB: $registerB, RegisterC: $registerC")
        }
        return accumulatedOutput
    }
}

fun main() {


    fun part1(input: List<String>) {
        val registerValues = input.take(3).map { it.substringAfter(": ").toInt() }
        val program = input.takeLast(1)[0].substringAfter("Program: ").split(",").map { it.toInt() }
        val device = StrangeDevice(registerValues[0], registerValues[1], registerValues[2], program)
        device.runProgram().joinToString(",").println()

    }


    fun part2(input: List<String>) {
        val registerValues = input.take(3).map { it.substringAfter(": ").toInt() }
        val program = input.takeLast(1)[0].substringAfter("Program: ").split(",").map { it.toInt() }
        var output = listOf<Int>()
        var registerA = 0
        while ((program == output).not()) {
            val device = StrangeDevice(registerA, registerValues[1], registerValues[2], program)
            output = device.runProgram()
            println("A: $registerA: $output")
            registerA++
        }


    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day17_test")
//    part1(testInput)
//    part2(testInput)
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day17")
//    part1(input)
    part2(input)
}
