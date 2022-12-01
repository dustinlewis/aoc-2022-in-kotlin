fun main() {
    // Works
    fun part1(input: List<String>): Int {
        val result: Pair<Int, String> = input.fold(Pair(0, "")) {
            acc, el -> Pair(if (el > acc.second) acc.first + 1 else acc.first, el)
        }
        return result.first
    }

    // one short
//    fun part1(input: List<String>): Int {
//        val result: Int = input.foldIndexed(0) {
//            index, acc, el -> if (index > 0 && el > input[index - 1]) acc + 1 else acc
//        }
//        return result
//    }

    // one short
//    fun part1(input: List<String>): Int {
//        val result: Int = input.zipWithNext().fold(0) {
//            acc, el -> if (el.second > el.first) acc + 1 else acc
//        }
//        return result
//    }

    fun part2(input: List<String>): Int {
        return input.windowed(3, 1) { win -> win.sumOf { it.toInt() } }
                .zipWithNext().fold(0) { acc, el -> if (el.second > el.first) acc + 1 else acc }
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("practice1")
    println(part1(input))
    println(part2(input))
}
