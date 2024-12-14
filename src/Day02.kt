fun main() {
    fun isReportValid(report: List<Int>): Boolean {
        if (report[0] > report[1]) {
            //decreasing
            for (i in 0 until report.size - 1) {
                val levelDiff = report[i] - report[i + 1]
                if (levelDiff < 1 || levelDiff > 3) {
                    return false
                }
            }
        } else {
            //increasing
            for (i in 1 until report.size) {
                var levelDiff = report[i] - report[i - 1]
                if (levelDiff < 1 || levelDiff > 3) {
                    return false
                }
            }

        }
        return true
    }

    fun part1(input: List<String>): Int {
        var safeReports = 0
        input.forEach { line ->
            val report = line.split(" ").map(String::toInt)
            if (isReportValid(report)) {
                safeReports++
            }
        }
        return safeReports
    }

    fun part2(input: List<String>): Int {
        var safeReports = 0

        input.forEach { line ->
            val report = line.split(" ").map(String::toInt)
            if (isReportValid(report)) {
                safeReports++
            } else {
                for (i in 0 until report.size) {
                    val newReport = report.subList(0, i) + report.subList(i + 1, report.size)
                    if (isReportValid(newReport)) {
                        safeReports++
                        return@forEach
                    }
                }
            }
        }
        return safeReports
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
