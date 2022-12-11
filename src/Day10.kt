import kotlin.math.floor

fun main() {
    fun part1(input: List<String>): Int {
        val instructions = input.map {
            val split = it.split(" ")
            if (split[0] == "noop") Pair(split[0], 0) else Pair(split[0], split[1].toInt())
        }
        println(instructions)

        val register = mutableListOf<Int>()
        var lastIncrease = 0
        instructions.forEach {
            val cycles = if(it.first == "noop") 1 else 2
            val newRegisterValue = if(register.isEmpty()) 1 else register[register.lastIndex] + lastIncrease
            for (c in 1..cycles) {
                register.add(newRegisterValue)
            }
            lastIncrease = it.second
        }

        val signalStrengths = mutableListOf<Int>()
        for(i in 20..220 step 40) {
            signalStrengths.add(i * register[i - 1])
        }
        println(signalStrengths)

        return signalStrengths.sum()
    }

    fun part2(input: List<String>): Int {
        val instructions = input.map {
            val split = it.split(" ")
            if (split[0] == "noop") Pair(split[0], 0) else Pair(split[0], split[1].toInt())
        }

        val register = mutableListOf<Int>()
        var lastIncrease = 0
        instructions.forEach {
            val cycles = if(it.first == "noop") 1 else 2
            val newRegisterValue = if(register.isEmpty()) 1 else register[register.lastIndex] + lastIncrease
            for (c in 1..cycles) {
                register.add(newRegisterValue)
            }
            lastIncrease = it.second
        }

        val screenLength = 40
        val screenRows = register
            .foldIndexed("") { index, acc, i ->
                val position = index - (floor((index) / screenLength.toDouble()) * screenLength).toInt()
    //            val position = when {
    //                index + 1 > 200 -> index - 200
    //                index + 1 > 160 -> index - 160
    //                index + 1 > 120 -> index - 120
    //                index + 1 > 80 -> index - 80
    //                index + 1 > 40 -> index - 40
    //                else -> index
    //            }
                acc + if((i - 1..i + 1).contains(position)) "#" else "."
            }
            .chunked(screenLength)

        screenRows.forEach(::println)

        return input.size
    }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}