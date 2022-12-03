fun main() {
    fun part1(input: List<String>): Int {
        val elves = mutableListOf<MutableList<Int>>()
        elves.add(mutableListOf())
        input.forEach {
            if (it.isEmpty()) {
                elves.add(mutableListOf())
            }
            else {
                elves[elves.lastIndex].add(it.toInt())
            }
        }
        return elves.maxOfOrNull { it.sum() } ?: 0
    }

    fun part2(input: List<String>): Int {
        val elves = mutableListOf<MutableList<Int>>()
        elves.add(mutableListOf())
        input.forEach {
            if (it.isEmpty()) {
                elves.add(mutableListOf())
            }
            else {
                elves[elves.lastIndex].add(it.toInt())
            }
        }
        return elves.map { it.sum() }.sortedDescending().take(3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
