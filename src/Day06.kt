fun main() {

    fun part1_orig(input: List<String>): Int =
        input[0].windowed(4, 1).map { it.toSet() }.withIndex().first { it.value.size == 4 }.index + 4

    fun part1(input: List<String>): Int =
        getIndexOfDistinctOfSize(input[0], 4)

    fun part2(input: List<String>): Int =
        getIndexOfDistinctOfSize(input[0], 14)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

fun getIndexOfDistinctOfSize(data: String, size: Int) =
    data.windowed(size, 1).map { it.toSet() }.withIndex().first { it.value.size == size }.index + size