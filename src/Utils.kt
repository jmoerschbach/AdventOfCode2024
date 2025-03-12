import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

//TODO: make these classes generic
data class Point(val x: Int, val y: Int) {
    fun distanceTo(other: Point): Int {
        return abs(x - other.x) + abs(y - other.y)
    }
}
data class PointLong(val x: Long, val y: Long) {
}

data class DirectedPoint (val point: Point, var direction: Direction, var cost : Long = Long.MAX_VALUE) {
    val x get() = point.x
    val y get() = point.y
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DirectedPoint

        return point == other.point

    }

    override fun hashCode(): Int {
        var result = cost.hashCode()
        result = 31 * result + point.hashCode()
        result = 31 * result + x
        result = 31 * result + y
        return result
    }
}

enum class Direction {
    LEFT, UP, RIGHT, DOWN;

    fun turnLeft(): Direction {
        return when (this) {
            LEFT -> DOWN
            UP -> LEFT
            RIGHT -> UP
            DOWN -> RIGHT
        }
    }

    fun turnRight(): Direction {
        return when (this) {
            LEFT -> UP
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
        }
    }
}

fun List<List<GridNode>>.isFree(pos: Point): Boolean {
    return pos.y in this.indices && pos.x in this[pos.y].indices && this[pos.y][pos.x].value != "#"
}

fun List<List<GridNode>>.isFree(pos: DirectedPoint): Boolean {
    return pos.y in this.indices && pos.x in this[pos.y].indices && this[pos.y][pos.x].value != "#"
}
