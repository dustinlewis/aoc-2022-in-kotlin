fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0) {
            acc, el ->
            val (firstHalf, secondHalf) = el.chunked(el.count()/2)
            acc + getIntersection(firstHalf, secondHalf).first().getPriority()
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).fold(0) {
            acc, el -> acc + getIntersection(*el.toTypedArray()).first().getPriority()
        }
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

val priorityMap =
    ('a'..'z').zip(1..26).toMap() +
    ('A'..'Z').zip(27..52).toMap()
fun Char.getPriority(): Int = priorityMap[this] ?: 0

//fun getIntersection(s1: String, s2: String): Set<Char> =
//    s1.toSet() intersect s2.toSet()
//
//fun getIntersection(strings: List<String>): Set<Char> =
//    strings.fold(strings.first().toSet()) { acc, s -> s.toSet() intersect acc }

fun getIntersection(vararg strings: String): Set<Char> =
    strings.fold(strings.first().toSet()) { acc, s -> s.toSet() intersect acc }