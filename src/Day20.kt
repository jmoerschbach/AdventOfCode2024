fun main() {

    fun evalPos(pos: Point, cost: Long, grid: List<List<GridNode>>): Boolean {
        if (grid.isFree(pos) && grid[pos.y][pos.x].costs == 1L) {
            grid[pos.y][pos.x].costs = 2
            return true
        }
        return false
    }

    fun findStartPosition(grid: List<List<GridNode>>, posToFind: String): Point {
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, node ->
                if (node.value == posToFind) return Point(x, y)
            }
        }
        return Point(0, 0)
    }

    fun bfs(grid: List<List<GridNode>>): List<Point> {
        val queue = mutableListOf<Point>()
        val start = findStartPosition(grid, "S")
        queue.add(start)
        grid[start.y][start.x].costs = 0
        var picoSeconds = 0
        val path = mutableListOf<Point>()
        while (queue.isNotEmpty()) {
            val current = queue.removeAt(0)
            val (x, y) = current

            path.add(current)
            val cost = grid[y][x].costs
            val newPosDown = Point(x, y + 1)
            val newPosUp = Point(x, y - 1)
            val newPosLeft = Point(x - 1, y)
            val newPosRight = Point(x + 1, y)

            if (grid[y][x].value == "E") {
                return path
            }

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
            picoSeconds++
        }
        return path
    }


    fun getIndexOfPoint(path: List<Point>, point: Point, startIndex: Int = 0): Int {
        path.forEachIndexed { index, p ->
            if (index > startIndex && p == point) {
                return index
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { it.map { GridNode(it.toString(), 1) }.toMutableList() }.toMutableList()
        val path = bfs(grid)

        val map = mutableMapOf<Int, Int>()
        path.forEachIndexed { index, point ->
            val (x, y) = point
            val newPosDown = Point(x, y + 2)
            val newPosUp = Point(x, y - 2)
            val newPosLeft = Point(x - 2, y)
            val newPosRight = Point(x + 2, y)

            val betterDown = getIndexOfPoint(path, newPosDown, index) - index - 2
            if (betterDown > 1) {
                map[betterDown] = map.getOrDefault(betterDown, 0) + 1
            }
            val betterUp = getIndexOfPoint(path, newPosUp, index) - index - 2
            if (betterUp > 1) {
                map[betterUp] = map.getOrDefault(betterUp, 0) + 1
            }
            val betterRight = getIndexOfPoint(path, newPosRight, index) - index - 2
            if (betterRight > 1) {
                map[betterRight] = map.getOrDefault(betterRight, 0) + 1
            }
            val betterLeft = getIndexOfPoint(path, newPosLeft, index) - index - 2
            if (betterLeft > 1) {
                map[betterLeft] = map.getOrDefault(betterLeft, 0) + 1
            }
        }


        return map.filterKeys { it >= 100 }.values.sum()

    }


    fun findPointWithDistance(path: List<Point>, start: Point, startIndex: Int): List<Point> {
        return path.filterIndexed { index, p -> index > startIndex && p.distanceTo(start) <=20 && p.distanceTo(start) > 1 }
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { it.map { GridNode(it.toString(), 1) }.toMutableList() }.toMutableList()
        val path = bfs(grid)
        val map = mutableMapOf<Int, Int>()
        path.forEachIndexed { index, currentPoint ->

            val cheatDestinations = findPointWithDistance(path, currentPoint, index)
            cheatDestinations.forEach { p ->
                val distance = p.distanceTo(currentPoint)
                val indexOfFarawayPoint = getIndexOfPoint(path, p, index)
                val saved = indexOfFarawayPoint - index - distance
                if (saved > 0) {
                    map[saved] = map.getOrDefault(saved, 0) + 1
                }
            }

        }
        return map.filterKeys { it >= 100 }.values.sum()

    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day20_test")
//    part1(testInput).println()
//    part2(testInput)
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day20")
    part1(input).println()
    part2(input).println()
}
