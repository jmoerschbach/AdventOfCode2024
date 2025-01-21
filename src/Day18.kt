fun main() {

    fun evalPos(pos: Point, cost: Long, grid: List<List<GridNode>>): Boolean {
        if (grid.isFree(pos) && grid[pos.y][pos.x].costs > cost + 1) {
            grid[pos.y][pos.x].costs = cost + 1
            return true
        }
        return false
    }

    fun bfs(grid: List<List<GridNode>>) {
        val queue = mutableListOf<Point>()
        val start = Point(0, 0)
        queue.add(start)
        grid[start.y][start.x].costs = 0
        while (queue.isNotEmpty()) {
            val current = queue.removeAt(0)
            val (x, y) = current

            val cost = grid[y][x].costs
            val newPosDown = Point(x, y + 1)
            val newPosUp = Point(x, y - 1)
            val newPosLeft = Point(x - 1, y)
            val newPosRight = Point(x + 1, y)

            if (evalPos(newPosDown, cost, grid)) {
                queue.add(newPosDown)
            }
            if (evalPos(newPosUp, cost, grid)) {
                queue.add(newPosUp)
            }
            if (evalPos(newPosLeft, cost, grid)) {
                queue.add(newPosLeft)
            }
            if (evalPos(newPosRight, cost, grid)) {
                queue.add(newPosRight)
            }
        }
    }

    fun createFreeGrid(): MutableList<MutableList<GridNode>> {
        val gridSize = 71
        val grid = MutableList(gridSize) {
            MutableList(gridSize) { GridNode(".") }
        }
        return grid
    }

    fun byteCoordinates(input: List<String>): List<Point> = input.map {
        val coords = it.split(",")
        Point(coords[0].toInt(), coords[1].toInt())
    }

    fun part1(input: List<String>): Long {
        val grid = createFreeGrid()

        val bytes = byteCoordinates(input)

        bytes.take(1024)
            .forEach { point ->
                grid[point.y][point.x].value = "#"
            }

        bfs(grid)
//        return grid[6][6].costs
        return grid[70][70].costs

    }

    fun List<List<GridNode>>.resetCosts() {
        forEach { row ->
            row.forEach { node ->
                node.costs = Long.MAX_VALUE
            }
        }
    }

    fun part2(input: List<String>) {
        val grid = createFreeGrid()

        val bytes = byteCoordinates(input)

        bytes.take(1024)
            .forEach { point ->
                grid[point.y][point.x].value = "#"
            }

        bytes.drop(1024).forEach { point ->
            grid[point.y][point.x].value = "#"
            grid.resetCosts()
            bfs(grid)
            if (grid[70][70].costs == Long.MAX_VALUE) {
                println(point)
                return
            }
        }


    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day18_test")
//    part1(testInput).println()
//    part2(testInput)
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day18")
    part1(input).println()
    part2(input)
}
