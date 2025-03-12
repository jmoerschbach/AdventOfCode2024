import kotlin.collections.mutableListOf

enum class Status { BLOCKED, VISITED, UNVISITED, LOOKING_LEFT, LOOKING_RIGHT, LOOKING_UP, LOOKING_DOWN }

private data class Row(var nodes: Array<Status>)
private data class Grid(var rows: Array<Row>) {
    fun get(x: Int, y: Int): Status {
        return rows[y].nodes[x]
    }

    fun set(x: Int, y: Int, status: Status) {
        rows[y].nodes[x] = status
    }

    fun run(): Int {
        var distinctPositions = 0
        var x = 0
        var y = 0
        // find the starting point
        for (row in 0 until rows.size) {
            for (col in 0 until rows[row].nodes.size) {
                val currentChar = rows[row].nodes[col]
                if (currentChar == Status.LOOKING_UP) {
                    y = row
                    x = col
                    break
                }
            }
        }
        println("x: $x, y: $y")

        //initial direction
        var direction = Direction.UP
        //where we start we have visited
        set(x, y, Status.VISITED)

        var nextX = x
        var nextY = y - 1
        var nextStatus = get(nextX, nextY)
        while (true) {

            nextStatus = get(nextX, nextY)

            when (nextStatus) {
                Status.BLOCKED -> {
                    direction = when (direction) {
                        Direction.UP -> {
                            nextY++
                            Direction.RIGHT
                        }

                        Direction.RIGHT -> {
                            nextX--
                            Direction.DOWN
                        }

                        Direction.DOWN -> {
                            nextY--
                            Direction.LEFT
                        }

                        Direction.LEFT -> {
                            nextX++
                            Direction.UP
                        }
                    }
                }

                Status.UNVISITED -> {
                    set(nextX, nextY, Status.VISITED)
//                    distinctPositions++
                }

                else -> {
                    //do nothing
                }
            }
            when (direction) {
                Direction.UP -> nextY--
                Direction.DOWN -> nextY++
                Direction.LEFT -> nextX--
                Direction.RIGHT -> nextX++
            }
            if (nextY < 0 || nextY >= rows.size || nextX < 0 || nextX >= rows[x].nodes.size) {
                break
            }
//            println("")
//            printGrid()
        }

        rows.forEach { row ->
            row.nodes.forEach { node ->
                if (node == Status.VISITED) {
                    distinctPositions++
                }
            }
        }
        return distinctPositions

    }

    fun printGrid() {

        rows.forEach { row ->
            row.nodes.forEach { node ->
                print(
                    when (node) {
                        Status.BLOCKED -> "#"
                        Status.VISITED -> "X"
                        Status.UNVISITED -> "."
                        Status.LOOKING_LEFT -> "<"
                        Status.LOOKING_RIGHT -> ">"
                        Status.LOOKING_UP -> "^"
                        Status.LOOKING_DOWN -> "v"
                    }
                )
            }
            println("")
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {

        val list = mutableListOf<Row>()
        input.forEach { line ->
            val row = line.map { it ->
                when (it) {
                    '#' -> Status.BLOCKED
                    '.' -> Status.UNVISITED
                    'X' -> Status.VISITED
                    '<' -> Status.LOOKING_LEFT
                    '>' -> Status.LOOKING_RIGHT
                    '^' -> Status.LOOKING_UP
                    'v' -> Status.LOOKING_DOWN
                    else -> Status.UNVISITED
                }
            }.toTypedArray()

            list.add(Row(row))
        }

        val grid = Grid(list.toTypedArray())
        grid.printGrid()

        return grid.run()


    }


    fun part2(input: List<String>): Int {
        return 0


    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day06_test")
//    part1(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
//    part2(input).println()
}
