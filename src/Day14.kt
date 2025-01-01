data class Robot(var currentPos: Point, val velocity: Point, val gridWidth: Int, val gridHeight: Int) {
    fun move() {
        currentPos = Point(currentPos.x + velocity.x, currentPos.y + velocity.y)
        if (currentPos.x < 0) {
            currentPos = currentPos.copy(x = gridWidth + currentPos.x)
        }
        if (currentPos.x >= gridWidth) {
            currentPos = currentPos.copy(x = currentPos.x % gridWidth)
        }
        if (currentPos.y < 0) {
            currentPos = currentPos.copy(y = gridHeight + currentPos.y)
        }
        if (currentPos.y >= gridHeight) {
            currentPos = currentPos.copy(y = currentPos.y % gridHeight)
        }
    }
}

const val gridWidth = 101
const val gridHeight = 103

fun List<List<String>>.printGrid() {
    this.forEach { row ->
        row.forEach { node ->
            print(node)
        }
        println("")
    }
}


fun main() {
    fun parsePoint(pos: String) =
        Point(pos.substringBefore(",").toInt(), pos.substringAfter(",").toInt())

    fun parse(input: List<String>): List<Robot> {
        return input.map { line ->
            val parts = line.split(" ")
            val position = parsePoint(parts[0].substringAfter("p="))
            val velocity = parsePoint(parts[1].substringAfter("v="))
            Robot(position, velocity, gridWidth, gridHeight)
        }
    }

    fun part1(input: List<String>): Int {

        val allRobots = parse(input)

        allRobots.forEach { robot ->
            repeat(100) { robot.move() }
        }

        val halfGridWidth = gridWidth / 2
        val halfGridHeight = gridHeight / 2
        val topLeft = allRobots.count { robot ->
            robot.currentPos.x < halfGridWidth && robot.currentPos.y < halfGridHeight
        }
        val topRight = allRobots.count { robot ->
            robot.currentPos.x > halfGridWidth && robot.currentPos.y < halfGridHeight
        }

        val bottomLeft = allRobots.count { robot ->
            robot.currentPos.x < halfGridWidth && robot.currentPos.y > halfGridHeight
        }
        val bottomRight = allRobots.count { robot ->
            robot.currentPos.x > halfGridWidth && robot.currentPos.y > halfGridHeight
        }

        return topRight * bottomLeft * topLeft * bottomRight

    }


    fun part2(input: List<String>): Int {
        // 15 seems to be a good number as the bottom end of the christmas tree is >15 #'s wide
        val minimumWidth = 15
        val allRobots = parse(input)

        for (i in 1 until 10_000) {


            allRobots.forEach {
                it.move()
            }
            val robotsInHorizontalLine =
                allRobots.groupBy { it.currentPos.y }.mapValues { it.value.map { it.currentPos.x } }
                    .filterValues { it.size > minimumWidth }

            val consecutiveAdjacentRobotsXPos = robotsInHorizontalLine.map { (_, xCoordinates) ->
                xCoordinates.sorted()
            }.map {
                it.zipWithNext().count { (a, b) ->
                    b - a == 1
                }
            }

            if (consecutiveAdjacentRobotsXPos.any { it > minimumWidth }) {
                val grid = MutableList(gridHeight) {
                    MutableList(gridWidth) { "." }
                }

                allRobots.forEach {
                    grid[it.currentPos.y][it.currentPos.x] = "#"
                }
                grid.printGrid()
                return i
            }

        }
        return -1
    }

    fun consecutiveRobots(rowOfRobots: List<String>): Int {
        return rowOfRobots.zipWithNext().count { (a, b) ->
            a == "#" && a == b
        }
    }

    fun part2_eachFrameDrawn(input: List<String>): Int {
        val allRobots = parse(input)

        for (i in 1 until 10_000) {

            val grid = MutableList(gridHeight) {
                MutableList(gridWidth) { "." }
            }

            allRobots.forEach {
                it.move()
                grid[it.currentPos.y][it.currentPos.x] = "#"
            }


            if (grid.any { row -> consecutiveRobots(row) > 15 }) {
                grid.printGrid()
                return i
            }
        }
        return -1
    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day14_test")
//    part1(testInput).println()
//    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
