fun main() {
    fun part1(input: List<String>): Long {
        return parseMonkeys(input)
            .executeRounds(20) { value -> value / 3L }
            .getMonkeyBusinessLevel()
    }

    fun part2(input: List<String>): Long {
        val monkeys = parseMonkeys(input)

        val commonMultiple: Int = monkeys
            .map { it.testDenominator }
            .distinct()
            .fold(1) { acc, denominator -> acc * denominator }

        return monkeys
            .executeRounds(10000) { value -> value % commonMultiple }
            .getMonkeyBusinessLevel()
    }

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

class Monkey(
    val items: ArrayDeque<Long> = ArrayDeque(),
    val operation: (value: Long) -> Long,
    val testDenominator: Int,
    val testTrueReceiver: Int,
    val testFalseReceiver: Int,
    val test: (value: Long) -> Boolean = { value -> value % testDenominator == 0L },
    var inspectionsPerformed: Long = 0L
) {
    override fun toString(): String {
        return "Items: $items, inspections: $inspectionsPerformed, testDenominator: $testDenominator, testTrueReceiver: $testTrueReceiver, testFalseReceiver: $testFalseReceiver"
    }

    fun inspectItems(
        trueReceiver: Monkey,
        falseReceiver: Monkey,
        reductionOperation: (value: Long) -> Long,
    ) {
        for(i in 0..items.lastIndex) {
            val item = reductionOperation(operation(items.removeFirst()))
            val receiver = if(test(item)) trueReceiver else falseReceiver
            receiver.items.addLast(item)
            inspectionsPerformed++
        }
    }
}

fun parseMonkeys(monkeyDataStrings: List<String>): List<Monkey> {
    return monkeyDataStrings
        .chunked(7)
        .map {
            Monkey(
                items = ArrayDeque(it[1]
                    .split(":")[1]
                    .split(",")
                    .map { n -> n.trim().toLong() }),
                operation = parseOperation(it[2]),
                testDenominator = it[3].split(" by ")[1].toInt(),
                testTrueReceiver =  it[4].split(" monkey ")[1].toInt(),
                testFalseReceiver =  it[5].split(" monkey ")[1].toInt(),
            )
        }
}

fun parseOperation(opString: String): (value: Long) -> Long {
    val opPieces = opString
        .split("=")[1]
        .split(" ")
        .filter { s -> s.isNotEmpty() }
        .map(String::trim)
    val isMultiplication = opPieces[1] == "*"
    return when {
        opPieces[0] == "old" && opPieces[2] == "old" -> {
            if(isMultiplication) { old -> old * old }
            else { old -> old + old }
        }
        opPieces[0] == "old" -> {
            if(isMultiplication) { old -> old * opPieces[2].toLong() }
            else { old -> old + opPieces[2].toLong() }
        }
        else -> {
            if(isMultiplication) { old -> opPieces[0].toLong() * old }
            else { old -> opPieces[0].toLong() + old }
        }
    }
}

fun List<Monkey>.executeRounds(
    rounds: Int,
    reductionOperation: (value: Long) -> Long
): List<Monkey> {
    for (round in 1..rounds) {
        forEach {
            it.inspectItems(
                trueReceiver = this[it.testTrueReceiver],
                falseReceiver = this[it.testFalseReceiver],
                reductionOperation = reductionOperation
            )
        }
    }
    return this
}

fun List<Monkey>.getMonkeyBusinessLevel(): Long {
    val sorted = sortedByDescending { it.inspectionsPerformed }
    return sorted[0].inspectionsPerformed * sorted[1].inspectionsPerformed
}