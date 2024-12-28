fun List<List<String>>.inRange(x: Int, y: Int): Boolean {
    return y in this.indices && x in this[y].indices
}

data class Region(val plantType: String, val gardenPlots: MutableSet<Pair<Int, Int>>) {
    fun isInRegion(x: Int, y: Int): Boolean {
        return gardenPlots.contains(Pair(x, y))
    }

    fun calculatePerimeter(): Int {
        return gardenPlots.sumOf { (x, y) ->
            var perimeter = 4

            if (isInRegion(x + 1, y)) {
                perimeter--
            }
            if (isInRegion(x - 1, y)) {
                perimeter--
            }
            if (isInRegion(x, y + 1)) {
                perimeter--
            }
            if (isInRegion(x, y - 1)) {
                perimeter--
            }
            perimeter
        }
    }

    fun calculatePerimeter2(): Int {
        var corners = 0
        gardenPlots.forEach { (x, y) ->
            val neighbors = listOf(
                Pair(x + 1, y) to Pair(x, y - 1),
                Pair(x - 1, y) to Pair(x, y - 1),
                Pair(x + 1, y) to Pair(x, y + 1),
                Pair(x - 1, y) to Pair(x, y + 1)
            )

            neighbors.forEach { (horizontal, vertical) ->
                if (!isInRegion(horizontal.first, horizontal.second) && !isInRegion(vertical.first, vertical.second)) {
                    corners++
                }
                if (isInRegion(horizontal.first, horizontal.second) && isInRegion(vertical.first, vertical.second) && !isInRegion(horizontal.first, vertical.second)) {
                    corners++
                }
            }
        }
        return corners
    }

    fun calculateArea() = gardenPlots.size

    fun calculatePrice() = calculateArea() * calculatePerimeter()
    fun calculatePrice2() = calculateArea() * calculatePerimeter2()

}

fun main() {

    fun searchForRegion(
        x: Int,
        y: Int,
        grid: List<List<String>>,
        region: Region
    ) {
        if (!grid.inRange(x, y)) {
            return
        }
        if (grid[y][x] != region.plantType) {
            return
        }
        if (region.isInRegion(x, y)) {
            return
        }
        region.gardenPlots.add(Pair(x, y))
        searchForRegion(x + 1, y, grid, region) //right
        searchForRegion(x - 1, y, grid, region) //left
        searchForRegion(x, y + 1, grid, region) //down
        searchForRegion(x, y - 1, grid, region) //up
    }

    fun findAllRegions(input: List<String>): MutableList<Region> {
        val grid = input.map { line ->
            line.map { it.toString() }
        }

        val allRegions = mutableListOf<Region>()
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, plantType ->
                if (allRegions.any { it.isInRegion(x, y) }) {
                    return@forEachIndexed
                }
                val region = Region(plantType, mutableSetOf())
                searchForRegion(x, y, grid, region)
                allRegions.add(region)
            }

        }
        return allRegions
    }

    fun part1(input: List<String>): Int {
        return findAllRegions(input).sumOf { it.calculatePrice() }
    }

    fun part2(input: List<String>): Int {
        return findAllRegions(input).sumOf { it.calculatePrice2() }

    }


// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day12_test")
//    part1(testInput).println()
//    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
