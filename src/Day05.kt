import jdk.internal.module.Checks
import kotlin.collections.mutableListOf

fun main() {

    fun isInvalidUpdate(currentPageIndex: Int, ruleSetToApply: List<Pair<Int, Int>>, updatedPages: List<Int>): Boolean {
        val matchingRulesForCurrentPage = ruleSetToApply.filter { it.first == updatedPages[currentPageIndex] }
        for (j in currentPageIndex + 1 until updatedPages.size) {
            if (matchingRulesForCurrentPage.none { it.second == updatedPages[j] }) {
                return true
            }
        }
        return false
    }

    fun findRuleSetToApply(
        allRules: MutableList<Pair<Int, Int>>,
        pageUpdate: List<Int>,
    ): List<Pair<Int, Int>> {
        return allRules.filter { pageUpdate.contains(it.first) && pageUpdate.contains(it.second) }
    }

    fun part1(input: List<String>): Int {
        var parseRules = true
        val allRules = mutableListOf<Pair<Int, Int>>()
        val allPageUpdates = mutableListOf<List<Int>>()

        //split and parse input into rules and page updates
        input.forEach { line ->
            if (line.isBlank()) {
                parseRules = false
                return@forEach
            }
            if (parseRules) {
                val rule = line.split("|").map(String::toInt)
                allRules.add(rule[0] to rule[1])
            } else {
                line.split(",").map { it.toInt() }.let { allPageUpdates.add(it) }
            }
        }

//        println(rules.size)
//        println(pageUpdates)

        val allValidUpdates = mutableListOf<List<Int>>()
        allPageUpdates.forEach { currentPageUpdate ->
            val ruleSetToApply = findRuleSetToApply(allRules, currentPageUpdate)

            for (currentPageIndex in 0 until currentPageUpdate.size) {
                if (isInvalidUpdate(currentPageIndex, ruleSetToApply, currentPageUpdate)) {
                    return@forEach
                }
            }
            allValidUpdates.add(currentPageUpdate)

        }


        println(allValidUpdates)

        var middlePageSum = 0
        allValidUpdates.forEach { vU ->
            middlePageSum += vU[(vU.size / 2)]
        }
        return middlePageSum
    }


    fun part2(input: List<String>): Int {
        var parseRules = true
        val allRules = mutableListOf<Pair<Int, Int>>()
        val allPageUpdates = mutableListOf<List<Int>>()

        //split and parse input into rules and page updates
        input.forEach { line ->
            if (line.isBlank()) {
                parseRules = false
                return@forEach
            }
            if (parseRules) {
                line.split("|").map(String::toInt).let { (first, second) -> allRules.add(first to second) }
            } else {
                line.split(",").map(String::toInt).let { allPageUpdates.add(it) }
            }
        }

//        println(rules.size)
//        println(pageUpdates)

        val allInvalidUpdates = mutableListOf<List<Int>>()
        allPageUpdates.forEach { currentPageUpdate ->
            val ruleSetToApply = findRuleSetToApply(allRules, currentPageUpdate)

            for (currentPageIndex in 0 until currentPageUpdate.size) {
                if (isInvalidUpdate(currentPageIndex, ruleSetToApply, currentPageUpdate)) {
                    allInvalidUpdates.add(currentPageUpdate)
                    return@forEach
                }
            }

        }

        val allCorrectedUpdates = mutableListOf<List<Int>>()

//        println(allInvalidUpdates)

        allInvalidUpdates.forEach { invalidUpdate ->
            val rules = findRuleSetToApply(allRules, invalidUpdate).map { it -> it.first }
//            println(rules)
            val reducedRules = rules.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }
//            println(reducedRules)
            val updateInCorrectOrder = reducedRules.map { it.first }.toMutableList()
            val lastMissingPage = invalidUpdate.minus(updateInCorrectOrder)
//            println(lastMissingPage)
            updateInCorrectOrder.addAll(lastMissingPage)
//            println(updateInCorrectOrder)
            allCorrectedUpdates.add(updateInCorrectOrder)
        }

        var middlePageSum = 0
        allCorrectedUpdates.forEach { vU ->
            middlePageSum += vU[(vU.size / 2)]
        }
        return middlePageSum
    }

// Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

// Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day05_test")
//    part2(testInput).println()
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 9)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
