fun main() {
    fun part1_original(input: List<String>): Int {
        val assignments = input.map { assignmentString ->
            val (firstStart, firstEnd, secondStart, secondEnd) =
                assignmentString.split(*"-,".toCharArray()).map { it.toInt() }
            listOf(
                Pair(firstStart, firstEnd),
                Pair(secondStart, secondEnd),
            )
        }
        return assignments.fold(0) {
            acc, (a1, a2) ->
            acc + if((a1.first <= a2.first && a1.second >= a2.second) ||
                (a2.first <= a1.first && a2.second >= a1.second)) 1 else 0
        }
    }

    fun part1(input: List<String>): Int {
        return input
            .map { assignments -> assignments.split('-', ',').map { it.toInt() } }
            .fold(0) { acc, (a1Start, a1End, a2Start, a2End) ->
                acc + if(doPairsFullyOverlap(Pair(a1Start, a1End), Pair(a2Start, a2End))) 1 else 0
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { assignments -> assignments.split('-', ',').map { it.toInt() } }
            .fold(0) { acc, (a1Start, a1End, a2Start, a2End) ->
                acc + if(doPairsOverlap(Pair(a1Start, a1End), Pair(a2Start, a2End))) 1 else 0
            }
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun doPairsFullyOverlap(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean =
    (p1.first <= p2.first && p1.second >= p2.second) || (p2.first <= p1.first && p2.second >= p1.second)

fun doPairsOverlap(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean =
    (p1.second >= p2.first && p1.second <= p2.second) || (p2.second >= p1.first && p2.second <= p1.second)