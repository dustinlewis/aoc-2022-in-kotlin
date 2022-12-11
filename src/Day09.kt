import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        // starting position counts as visited
        val tailVisits = mutableSetOf<Position>()
        var headPosition = Position(0, 0)
        var tailPosition = Position(0, 0)

        tailVisits.add(tailPosition)

        val moves = input.map {
            val split = it.split(" ")
            Pair(split[0], split[1].toInt())
        }
        //println(moves)

        moves.forEach { (direction, count) ->
            for (i in 1..count) {
                when (direction) {
                    "L" -> {
                        headPosition = Position(headPosition.first - 1, headPosition.second)
                        tailPosition = getNewTailPosition(headPosition, tailPosition)
                        tailVisits.add(tailPosition)
                    }
                    "R" -> {
                        headPosition = Position(headPosition.first + 1, headPosition.second)
                        tailPosition = getNewTailPosition(headPosition, tailPosition)
                        tailVisits.add(tailPosition)
                    }
                    "U" -> {
                        headPosition = Position(headPosition.first, headPosition.second + 1)
                        tailPosition = getNewTailPosition(headPosition, tailPosition)
                        tailVisits.add(tailPosition)
                    }
                    "D" -> {
                        headPosition = Position(headPosition.first, headPosition.second - 1)
                        tailPosition = getNewTailPosition(headPosition, tailPosition)
                        tailVisits.add(tailPosition)
                    }
                }
            }
        }

        return tailVisits.count()
    }

    fun part2(input: List<String>): Int {
        // starting position counts as visited
        val tailVisits = mutableSetOf<Position>()
        val startPosition = Position(0, 0)
        val positions = (0..9).associateWith { startPosition }.toMutableMap()
        //println(positions)

        tailVisits.add(startPosition)

        val moves = input.map {
            val split = it.split(" ")
            Pair(split[0], split[1].toInt())
        }
        //println(moves)

        moves.forEach { (direction, count) ->
            for (c in 1..count) {
                when (direction) {
                    "L" -> {
                        val headPosition = positions[0]!!
                        positions[0] = Position(headPosition.first - 1, headPosition.second)
                        for(i in 1..9) {
                            positions[i] = getNewTailPosition(positions[i - 1]!!, positions[i]!!)
                        }
                        tailVisits.add(positions[9]!!)
                    }
                    "R" -> {
                        val headPosition = positions[0]!!
                        positions[0] = Position(headPosition.first + 1, headPosition.second)
                        for(i in 1..9) {
                            positions[i] = getNewTailPosition(positions[i - 1]!!, positions[i]!!)
                        }
                        tailVisits.add(positions[9]!!)
                    }
                    "U" -> {
                        val headPosition = positions[0]!!
                        positions[0] = Position(headPosition.first, headPosition.second + 1)
                        for(i in 1..9) {
                            positions[i] = getNewTailPosition(positions[i - 1]!!, positions[i]!!)
                        }
                        tailVisits.add(positions[9]!!)
                    }
                    "D" -> {
                        val headPosition = positions[0]!!
                        positions[0] = Position(headPosition.first, headPosition.second - 1)
                        for(i in 1..9) {
                            positions[i] = getNewTailPosition(positions[i - 1]!!, positions[i]!!)
                        }
                        tailVisits.add(positions[9]!!)
                    }
                }
//                println(positions)

//                // print the Day09test2 grid after each move
//                var bridge = ""
//                for(r in 4 downTo 0) {
//                    for(c in 0..5) {
//                        val rope = positions.toList().find { it.second == Pair(c, r) }
//                        bridge += rope?.first?.toString() ?: "."
//                    }
//                }
//                bridge.windowed(6, 6).forEach(::println)
            }
        }

        return tailVisits.count()
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

// Row, Column
typealias Position = Pair<Int, Int>

fun getNewTailPosition(head: Position, tail: Position): Position {
    val xDiff = abs(head.first - tail.first)
    val yDiff = abs(head.second - tail.second)
    if(xDiff > 1 && yDiff > 1) {
        return Position(
            if(head.first < tail.first) tail.first - 1 else tail.first + 1,
            if(head.second < tail.second) tail.second - 1 else tail.second + 1
        )
    }
    if(xDiff > 1) {
        return Position(
            if(head.first < tail.first) tail.first - 1 else tail.first + 1,
            head.second
        )
    }
    if(yDiff > 1) {
        return Position(
            head.first,
            if(head.second < tail.second) tail.second - 1 else tail.second + 1
        )
    }
    return tail
}