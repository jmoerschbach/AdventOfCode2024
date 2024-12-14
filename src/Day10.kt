data class Point(val y: Int, val x: Int)
typealias Path = List<Point>

fun main() {


    fun isTrailHeadSearch(
        row: Int,
        col: Int,
        map: List<List<Int>>,
        last: Int,
        startingPoint: Point,
        foundTrailheads: MutableMap<Point, MutableSet<Point>>
    ) {
        if (row < 0 || row >= map.size || col < 0 || col >= map[0].size) {
            return
        }
        val current = map[row][col]
        if (current - last != 1) {
            return
        }
        if (current == 9) {
            val peaks = foundTrailheads.getOrPut(startingPoint) { mutableSetOf() }
            peaks.add(Point(row, col))
            return
        }

        isTrailHeadSearch(row + 1, col, map, current, startingPoint, foundTrailheads)
        isTrailHeadSearch(row - 1, col, map, current, startingPoint, foundTrailheads)
        isTrailHeadSearch(row, col + 1, map, current, startingPoint, foundTrailheads)
        isTrailHeadSearch(row, col - 1, map, current, startingPoint, foundTrailheads)
    }

    fun part1(input: List<String>): Int {
        val map = input.map { it.map { it.toString().toInt() } }

        val allTrailHeads = mutableMapOf<Point, MutableSet<Point>>()
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 0) {
                    isTrailHeadSearch(i + 1, j, map, map[i][j], Point(i, j), allTrailHeads)
                    isTrailHeadSearch(i - 1, j, map, map[i][j], Point(i, j), allTrailHeads)
                    isTrailHeadSearch(i, j + 1, map, map[i][j], Point(i, j), allTrailHeads)
                    isTrailHeadSearch(i, j - 1, map, map[i][j], Point(i, j), allTrailHeads)
                }
            }
        }
        return allTrailHeads.values.sumOf { it.size }
    }

    fun isTrailHeadSearchWithPath(
        row: Int,
        col: Int,
        map: List<List<Int>>,
        last: Int,
        startingPoint: Point,
        foundTrailheads: MutableMap<Point, MutableSet<Path>>,
        currentPath: Path
    ) {
        if (row < 0 || row >= map.size || col < 0 || col >= map[0].size) {
            return
        }
        val current = map[row][col]
        if (current - last != 1) {
            return
        }
        val newCurrentPath = currentPath + Point(row, col)
        if (current == 9) {
            val peaks = foundTrailheads.getOrPut(startingPoint) { mutableSetOf() }
            peaks.add(newCurrentPath)
            return
        }

        isTrailHeadSearchWithPath(row + 1, col, map, current, startingPoint, foundTrailheads, newCurrentPath)
        isTrailHeadSearchWithPath(row - 1, col, map, current, startingPoint, foundTrailheads, newCurrentPath)
        isTrailHeadSearchWithPath(row, col + 1, map, current, startingPoint, foundTrailheads, newCurrentPath)
        isTrailHeadSearchWithPath(row, col - 1, map, current, startingPoint, foundTrailheads, newCurrentPath)
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.map { it.toString().toInt() } }

        val allTrailHeadsWithPaths = mutableMapOf<Point, MutableSet<List<Point>>>()
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 0) {
                    val currentPath = listOf(Point(i, j))
                    isTrailHeadSearchWithPath(
                        i + 1,
                        j,
                        map,
                        map[i][j],
                        Point(i, j),
                        allTrailHeadsWithPaths,
                        currentPath
                    )
                    isTrailHeadSearchWithPath(
                        i - 1,
                        j,
                        map,
                        map[i][j],
                        Point(i, j),
                        allTrailHeadsWithPaths,
                        currentPath
                    )
                    isTrailHeadSearchWithPath(
                        i,
                        j + 1,
                        map,
                        map[i][j],
                        Point(i, j),
                        allTrailHeadsWithPaths,
                        currentPath
                    )
                    isTrailHeadSearchWithPath(
                        i,
                        j - 1,
                        map,
                        map[i][j],
                        Point(i, j),
                        allTrailHeadsWithPaths,
                        currentPath
                    )
                }
            }
        }
        return allTrailHeadsWithPaths.values.sumOf { it.size }
    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day10_test")
//    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
