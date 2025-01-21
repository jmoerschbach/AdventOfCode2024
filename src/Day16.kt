data class GridNode(var value: String, var costs: Long = Long.MAX_VALUE) {
    override fun toString(): String {
        if (costs != Long.MAX_VALUE) {
            return String.format("%-5s", costs)
        }
        return value.repeat(5)
    }
}


    fun List<List<GridNode>>.printGrid() {
        this.forEach { row ->
            row.forEach { node ->
                print(node)
            }
            println("")
        }
    }

fun main() {



    fun findStartPosition(grid: List<List<GridNode>>, posToFind: String): Point {
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, node ->
                if (node.value == posToFind) return Point(x, y)
            }
        }
        return Point(0, 0)
    }

    fun nextPosition(direction: Direction, x: Int, y: Int): DirectedPoint = when (direction) {
        Direction.LEFT -> DirectedPoint(Point(x - 1, y), direction)
        Direction.UP -> DirectedPoint(Point(x, y - 1), direction)
        Direction.RIGHT -> DirectedPoint(Point(x + 1, y), direction)
        Direction.DOWN -> DirectedPoint(Point(x, y + 1), direction)
    }

    fun bfs(grid: List<List<GridNode>>) {
        val queue = mutableListOf<DirectedPoint>()
        val start = DirectedPoint(findStartPosition(grid, "S"), Direction.RIGHT)
        queue.add(start)
        grid[start.y][start.x].costs = 0
        while (queue.isNotEmpty()) {
            val current = queue.removeAt(0)
            val (x, y) = current.point
            val direction = current.direction

            val cost = grid[y][x].costs
            val newPosStraight = nextPosition(direction, x, y)
            val newPosAfterLeftTurn = nextPosition(direction.turnLeft(), x, y)
            val newPosAfterRightTurn = nextPosition(direction.turnRight(), x, y)
            if (grid[newPosStraight.y][newPosStraight.x].costs > cost + 1 && grid.isFree(newPosStraight)) {
                grid[newPosStraight.y][newPosStraight.x].costs = cost + 1
                queue.add(newPosStraight)
            }
            if (grid[newPosAfterLeftTurn.y][newPosAfterLeftTurn.x].costs > cost + 1 + 1000 && grid.isFree(
                    newPosAfterLeftTurn
                )
            ) {
                grid[newPosAfterLeftTurn.y][newPosAfterLeftTurn.x].costs = cost + 1 + 1000
                queue.add(newPosAfterLeftTurn)
            }
            if (grid[newPosAfterRightTurn.y][newPosAfterRightTurn.x].costs > cost + 1 + 1000 && grid.isFree(
                    newPosAfterRightTurn
                )
            ) {
                grid[newPosAfterRightTurn.y][newPosAfterRightTurn.x].costs = cost + 1 + 1000
                queue.add(newPosAfterRightTurn)
            }
        }
    }

    fun bfsReverse(grid: List<List<GridNode>>): Set<DirectedPoint> {
        val goodPositions = mutableSetOf<DirectedPoint>()
        val queue = mutableListOf<DirectedPoint>()
        val startPos = findStartPosition(grid, "E")
        val start1 = DirectedPoint(startPos, Direction.LEFT, grid[startPos.y][startPos.x].costs)
        val start2 = DirectedPoint(startPos, Direction.DOWN, grid[startPos.y][startPos.x].costs)
        goodPositions.add(start1)
        queue.add(start1)
        queue.add(start2)

        while (queue.isNotEmpty()) {
            val current = queue.removeAt(0)
            val (x, y) = current.point
            val cost = current.cost
            val direction = current.direction

            val newPosStraight = nextPosition(direction, x, y)
            val newPosAfterLeftTurn = nextPosition(direction.turnLeft(), x, y)
            val newPosAfterRightTurn = nextPosition(direction.turnRight(), x, y)

            val newCostStraight = cost - 1
            val newCostLeft = cost - 1 - 1000
            val newCostRight = cost - 1 - 1000

            val costStraight = grid[newPosStraight.y][newPosStraight.x].costs
            val costLeft = grid[newPosAfterLeftTurn.y][newPosAfterLeftTurn.x].costs
            val costRight = grid[newPosAfterRightTurn.y][newPosAfterRightTurn.x].costs

            if ((newCostStraight == costStraight || newCostStraight - 1000 == costStraight) && grid.isFree(
                    newPosStraight
                )
            ) {
                queue.add(newPosStraight.copy(cost = newCostStraight))
                goodPositions.add(newPosStraight)
            }
            if ((newCostLeft == costLeft || newCostLeft - 1000 == costLeft) && grid.isFree(newPosAfterLeftTurn)) {
                queue.add(newPosAfterLeftTurn.copy(cost = newCostLeft))
                goodPositions.add(newPosAfterLeftTurn)
            }
            if ((newCostRight == costRight || newCostRight - 1000 == costRight) && grid.isFree(newPosAfterRightTurn)) {
                queue.add(newPosAfterRightTurn.copy(cost = newCostRight))
                goodPositions.add(newPosAfterRightTurn)
            }
        }
        return goodPositions
    }

//    fun move(
//        pos: Point,
//        currentDirection: Direction,
//        grid: MutableList<MutableList<GridNode>>,
//        currentPath: MutableList<Point>,
//        successfulPaths: MutableList<BestCostPath>,
//        costs: Long,
//        target: String = "E"
//    ) {
//        val (x, y) = pos
//        if (grid[y][x].value == "#") {
//            return
//        }
//        if (costs > grid[y][x].costs) {
//            println("Current costs $costs are higher than ${grid[y][x].costs} at ($x,$y)")
//            return
//        }
//        if (grid[y][x].value == target) {
//            println("#################")
//            println("Found exit at $pos with costs $costs")
//            println("#################")
//            successfulPaths.add(BestCostPath(currentPath + pos, costs))
//            return
//        }
//
//        grid[y][x].costs = costs
////        currentPath.add(grid[y][x])
//        val newCurrentPath = currentPath.toMutableList()
//        newCurrentPath.add(pos)
//        val newPosStraight = nextPosition(currentDirection, x, y)
//        val newPosAfterLeftTurn = nextPosition(currentDirection.turnLeft(), x, y)
//        val newPosAfterRightTurn = nextPosition(currentDirection.turnRight(), x, y)
//
//        move(
//            newPosAfterLeftTurn,
//            currentDirection.turnLeft(),
//            grid,
//            newCurrentPath,
//            successfulPaths,
//            costs + 1 + 1000,
//            target
//        )
//        move(
//            newPosAfterRightTurn,
//            currentDirection.turnRight(),
//            grid,
//            newCurrentPath,
//            successfulPaths,
//            costs + 1 + 1000,
//            target
//        )
//        move(newPosStraight, currentDirection, grid, newCurrentPath, successfulPaths, costs + 1, target)
//
//    }


    fun readGrid(input: List<String>): MutableList<MutableList<GridNode>> {
        return input.map { line ->
            line.map {
                GridNode(it.toString())
            }.toMutableList()
        }.toMutableList()
    }

    fun part1(input: List<String>): Int {
        val grid = readGrid(input)

        bfs(grid)

        val endPos = findStartPosition(grid, "E")
        return grid[endPos.y][endPos.x].costs.toInt()
    }


    fun part2(input: List<String>): Int {
        val grid = readGrid(input)

        bfs(grid)
        val positions = bfsReverse(grid)
        return positions.size

    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day16_test")
//    part1(testInput).println()
//    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
