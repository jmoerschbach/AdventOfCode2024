import kotlin.toString

fun main() {

    fun List<List<String>>.printGrid() {
        this.forEach { row ->
            row.forEach { node ->
                print(node)
            }
            println("")
        }
    }

    fun findOffset(x: Int, y: Int, grid: List<List<String>>, direction: Char): Int {
        val directions = mapOf(
            '<' to Pair(-1, 0),
            '>' to Pair(1, 0),
            '^' to Pair(0, -1),
            'v' to Pair(0, 1)
        )
        val (dx, dy) = directions[direction] ?: Pair(0, 0)
        var rightEdge = false
        var leftEdge = false
        if (direction == '^') {
            rightEdge = grid[y - 1][x] == "]"
            leftEdge = grid[y - 1][x] == "["
        } else if (direction == 'v') {
            rightEdge = grid[y + 1][x ] == "]"
            leftEdge = grid[y + 1][x ] == "["
        }

        var offset = 0
        var newX = x + dx
        var newY = y + dy

        while (newY in grid.indices && newX in grid[newY].indices) {
            if (
                grid[newY][newX] == "#"
                || (rightEdge && grid[newY][newX + 1] == "#")
                || (leftEdge && grid[newY][newX - 1] == "#")
            )
                return 0
            if (grid[newY][newX] == ".") return offset + 1
            newX += dx
            newY += dy
            offset++
        }
//        while (true) {
//            if (grid[newY][newX] == "#") {
//                return 0
//            }
//            offset++
//            if (grid[newY][newX] == ".") {
//                return offset
//            }
//            newX += dx
//            newY += dy
//        }
        return offset
    }

    fun findCurrentPosition(grid: List<List<String>>): Pair<Int, Int> {
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, value ->
                if (value == "@") return Pair(x, y)
            }
        }
        return Pair(0, 0)
    }

    fun moveInGrid(grid: List<MutableList<String>>, command: Char) {
        val (currentX, currentY) = findCurrentPosition(grid)
        val offset = findOffset(currentX, currentY, grid, command)
//            println("X: $currentX, Y; $currentY, Offset: $offset, command: $command")
        if (command == '^') {
            for (y in currentY - offset until currentY) {
                grid[y][currentX] = grid[y + 1][currentX]
            }
        } else if (command == 'v') {
            for (y in currentY + offset downTo currentY + 1) {
                grid[y][currentX] = grid[y - 1][currentX]
            }

        } else if (command == '<') {
            for (x in currentX - offset until currentX) {
                grid[currentY][x] = grid[currentY][x + 1]
            }

        } else if (command == '>') {
            for (x in currentX + offset downTo currentX + 1) {
                grid[currentY][x] = grid[currentY][x - 1]
            }
        }
        if (offset > 0) {
            grid[currentY][currentX] = "."
        }

    }

    fun calculateGpsSum(grid: List<List<String>>): Int {
        var gpsSum = 0
        for (x in grid.indices) {
            for (y in grid[x].indices) {
                if (grid[x][y] == "O") {
                    gpsSum += 100 * x + y
                }
            }
        }
        return gpsSum
    }

    fun part1(input: List<String>): Int {
        val grid = input.dropLast(2).map { line -> line.map(Char::toString).toMutableList() }.toMutableList()
        val commands = input.drop(input.size - 1)[0]

        commands.forEach { command ->
            moveInGrid(grid, command)
        }



        return calculateGpsSum(grid)

//        return grid.sumOf { row -> row.indices.sumOf { y -> if (row[y] == "O") 100 * grid.indexOf(row) + y else 0 } }
    }

    fun moveInGridPart2(grid: List<MutableList<String>>, command: Char) {
        val (currentX, currentY) = findCurrentPosition(grid)
        val offset = findOffset(currentX, currentY, grid, command)
//            println("X: $currentX, Y; $currentY, Offset: $offset, command: $command")
        if (command == '^') {
            val rightEdge = grid[currentY - 1][currentX] == "]"
            val leftEdge = grid[currentY - 1][currentX] == "["
            for (y in currentY - offset until currentY) {
                grid[y][currentX] = grid[y + 1][currentX]
                if(rightEdge) {
                    grid[y][currentX + 1] = grid[y + 1][currentX + 1]
                    grid[y][currentX - 1] = grid[y + 1][currentX - 1]
                    grid[y][currentX -2] = grid[y + 1][currentX - 2]
                } else if(leftEdge) {
                    grid[y][currentX + 1] = grid[y + 1][currentX + 1]
                    grid[y][currentX - 1] = grid[y + 1][currentX - 1]
                    grid[y][currentX + 2] = grid[y + 1][currentX + 2]
                }
//                if (grid[y + 1][currentX + 1] == "]" || grid[y + 1][currentX + 1] == "[") {
//                    grid[y][currentX + 1] = grid[y + 1][currentX + 1]
//                }
//                if (grid[y + 1][currentX - 1] == "]" || grid[y + 1][currentX - 1] == "[") {
//                    grid[y][currentX - 1] = grid[y + 1][currentX - 1]
//                }

                grid.printGrid()
            }
            if (offset > 0) {
                grid[currentY][currentX] = "."
                if (rightEdge) {
                    grid[currentY][currentX - 1] = "."
                }
                if (leftEdge) {
                    grid[currentY][currentX + 1] = "."
                }
            }
        } else if (command == 'v') {
            for (y in currentY + offset downTo currentY + 1) {
                grid[y][currentX] = grid[y - 1][currentX]
            }

        } else if (command == '<') {
            for (x in currentX - offset until currentX) {
                grid[currentY][x] = grid[currentY][x + 1]
            }

        } else if (command == '>') {
            for (x in currentX + offset downTo currentX + 1) {
                grid[currentY][x] = grid[currentY][x - 1]
            }
        }
        if (offset > 0) {
            grid[currentY][currentX] = "."
        }

    }

    fun part2(input: List<String>): Int {
        val grid = input.dropLast(2).map { line ->
            line.flatMap {
                when (it) {
                    '.' -> listOf(".", ".")
                    '#' -> listOf("#", "#")
                    'O' -> listOf("[", "]")
                    '@' -> listOf("@", ".")
                    else -> listOf(it.toString())
                }
            }.toMutableList()
        }.toMutableList()
        println(grid)
        val commands = input.drop(input.size - 1)[0]

        grid.printGrid()
        println(commands)

        commands.forEach { command ->
            moveInGridPart2(grid, command)
            grid.printGrid()
        }
        return 0
    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day15_test")
    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day15")
//    part1(input).println()
//    part2(input).println()
}
