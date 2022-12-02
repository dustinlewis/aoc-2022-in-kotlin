fun main() {
    fun part1(input: List<String>): Int {
        val elfs = mutableListOf<MutableList<Int>>()
        elfs.add(mutableListOf())
        input.forEach() {
            if (it.isEmpty()) {
                elfs.add(mutableListOf())
            }
            else {
                elfs[elfs.lastIndex].add(it.toInt())
            }
        }
        val totals = elfs.map { it.sum() }

//        val elfs = input.fold(mutableListOf<MutableList<String>>()) {
//            acc, el ->
//            {
//                if (el.isEmpty()) acc.add(mutableListOf()) else acc[acc.lastIndex].add(el)
//                acc
//            }
//        }
        return totals.max()
    }

    fun part2(input: List<String>): Int {
        val elfs = mutableListOf<MutableList<Int>>()
        elfs.add(mutableListOf())
        input.forEach() {
            if (it.isEmpty()) {
                elfs.add(mutableListOf())
            }
            else {
                elfs[elfs.lastIndex].add(it.toInt())
            }
        }
        val totals = elfs.map { it.sum() }
        return totals.sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
