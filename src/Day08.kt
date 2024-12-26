data class Node(val isAntenna: Boolean, val frequency: String, var isAntinode: Boolean) {
    override fun toString(): String {
        return if (isAntinode) {
            "#"
        } else {
            frequency
        }
    }
}

fun List<List<Node>>.printGrid() {
    this.forEach { row ->
        row.forEach { node ->
            print(node)
        }
        println("")
    }
}

fun List<List<Node>>.inRange(x: Int, y: Int): Boolean {
    return y in this.indices && x in this[y].indices
}

fun List<List<Node>>.sumOfAntinodes(): Int {
    return this.sumOf { it.count { it.isAntinode } }
}

fun main() {
    fun createGrid(input: List<String>): MutableList<MutableList<Node>> = input.map {
        it.map {
            val c = it.toString()
            val isAntenna = c != "." && c != "#"
            Node(isAntenna, c, false)
        }.toMutableList()
    }.toMutableList()

    fun setAntinode(x: Int, y: Int, grid: MutableList<MutableList<Node>>) {
        if (grid.inRange(x, y))
            grid[y][x].isAntinode = true
    }

    fun searchAntennasForAntennaAt(originX: Int, originY: Int, grid: MutableList<MutableList<Node>>) {
        val origin = grid[originY][originX]
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, node ->
                if (y == originY && x == originX) {
                    // skip the original antenna
                    return@forEachIndexed
                }
                if (node.isAntenna && node.frequency == origin.frequency) {
                    val dx = originX - x
                    val dy = originY - y
                    setAntinode(originX + dx, originY + dy, grid)
                    setAntinode(x - dx, y - dy, grid)
                }
            }
        }
    }


    fun part1(input: List<String>): Int {
        val grid = createGrid(input)
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, node ->
                if (node.isAntenna) {
                    searchAntennasForAntennaAt(x, y, grid)
                }
            }
        }
        return grid.sumOfAntinodes()
    }

    fun setAntinodeRepeating(x: Int, y: Int, dx: Int, dy: Int, grid: MutableList<MutableList<Node>>) {
        var nextAntinodeX = x + dx
        var nextAntinodeY = y + dy
        while (grid.inRange(nextAntinodeX, nextAntinodeY)) {
            setAntinode(nextAntinodeX, nextAntinodeY, grid)
            nextAntinodeX += dx
            nextAntinodeY += dy
        }
    }

    fun repeatingSearchAntennasForAntennaAt(originX: Int, originY: Int, grid: MutableList<MutableList<Node>>) {
        val origin = grid[originY][originX]
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, node ->
                if (y == originY && x == originX) {
                    // skip the original antenna
                    return@forEachIndexed
                }
                if (node.isAntenna && node.frequency == origin.frequency) {

//                    println("x: $x, y: $y:${node}")
                    val dx = originX - x
                    val dy = originY - y
                    setAntinodeRepeating(originX, originY, dx, dy, grid)
                    setAntinodeRepeating(originX, originY, -dx, -dy, grid)
                }
            }
        }
    }
    

    fun part2(input: List<String>): Int {
        val grid = createGrid(input)
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, node ->
                if (node.isAntenna) {
                    repeatingSearchAntennasForAntennaAt(x, y, grid)

                }
            }
        }
        return grid.sumOfAntinodes()
    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day08_test")
//    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
