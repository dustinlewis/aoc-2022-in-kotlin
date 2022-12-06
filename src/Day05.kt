fun main() {
    fun part1(input: List<String>): String {
//        println(input)
//        val splitIndex = input.indexOf("")
//        val stackData = input.subList(0, splitIndex - 1)
//        val transitionData = input.subList(splitIndex + 1, input.lastIndex)
        val (transitionData, stackData) = input.partition { it.contains("move") }
//        println(stackData)
//        println(transitionData)
        val stacks = (1..9).associateWith { ArrayDeque<String>() }

        stackData
            .reversed()
            .drop(2)
            .map { s ->
                s.chunked(4).map {
                    if(it.isBlank()) "" else it.replace("""[\[\] ]""".toRegex(), "")
                }
            }
            .forEach { crates ->
                crates.forEachIndexed { index, letter ->
                    if (letter.isNotEmpty()) {
                        stacks[index + 1]?.addLast(letter)
                    }
                }
            }

        // [qty, start, dest]
        // [2, 2, 8]
        val transitions = transitionData.map { it.split(" ").mapNotNull { s -> s.toIntOrNull() } }
        transitions.forEach { (quantity, start, destination) ->
            for(i in 1..quantity) {
                stacks[destination]?.addLast(stacks[start]?.removeLast().orEmpty())
            }
        }

        return stacks.values.fold("") { acc, stack -> acc + stack.last() }
    }

    fun part2(input: List<String>): String {
        val (transitionData, stackData) = input.partition { it.contains("move") }
        val stacks = (1..9).associateWith { ArrayDeque<String>() }

        stackData
            .reversed()
            .drop(2)
            .map { s ->
                s.chunked(4).map {
                    if(it.isBlank()) "" else it.replace("""[\[\] ]""".toRegex(), "")
                }
            }
            .forEach { crates ->
                crates.forEachIndexed { index, letter ->
                    if (letter.isNotEmpty()) {
                        stacks[index + 1]?.addLast(letter)
                    }
                }
            }

        // [qty, start, dest]
        // [2, 2, 8]
        val transitions = transitionData.map { it.split(" ").mapNotNull { s -> s.toIntOrNull() } }
        transitions.forEach { (quantity, start, destination) ->
            val temp = ArrayDeque<String>()
            for(i in 1..quantity) {
                temp.addLast(stacks[start]?.removeLast().orEmpty())
            }
            for(i in 1..quantity) {
                stacks[destination]?.addLast(temp.removeLast())
            }
        }

        return stacks.values.fold("") { acc, stack -> acc + stack.last() }
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

