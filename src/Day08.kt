import kotlin.text.forEachIndexed as forEachIndexed2

fun main() {
    fun part1(input: List<String>): Int {
        val edgeCount = input.count() * 2 + (input.count() - 2) * 2

        val topBottom = input.fold(MutableList(input.size){ "" }) { acc, element ->
            element.withIndex().forEach { c -> acc[c.index] = acc[c.index] + c.value }
            acc
        }

        var visibleInteriorCount = 0
        input.forEachIndexed { rowIndex, s ->
            if (rowIndex == 0 || rowIndex == input.lastIndex) return@forEachIndexed
            s.forEachIndexed2 { colIndex, c ->
                if (colIndex == 0 || colIndex == input.lastIndex) return@forEachIndexed2
                // check left
                val isVisibleLeft = !s.substring(0, colIndex).any { it >= c }
                if (isVisibleLeft) {
                     visibleInteriorCount++
                    return@forEachIndexed2
                }
                // check right
                val isVisibleRight = !s.substring(colIndex + 1, s.length).any { it >= c }
                if (isVisibleRight) {
                    visibleInteriorCount++
                    return@forEachIndexed2
                }
                // check above
                val isVisibleAbove = !topBottom[colIndex].substring(0, rowIndex).any { it >= c }
                if (isVisibleAbove) {
                    visibleInteriorCount++
                    return@forEachIndexed2
                }
                // check below
                val isVisibleBelow = !topBottom[colIndex].substring(rowIndex + 1, topBottom[colIndex].length).any { it >= c }
                if (isVisibleBelow) {
                    visibleInteriorCount++
                    return@forEachIndexed2
                }
            }
        }
        return visibleInteriorCount + edgeCount
    }

    fun part2(input: List<String>): Int {
        val topBottom = input.fold(MutableList(input.size){ "" }) { acc, element ->
            element.withIndex().forEach { c -> acc[c.index] = acc[c.index] + c.value }
            acc
        }

        val scenicScores = mutableListOf<Int>()
        input.forEachIndexed { rowIndex, s ->
            if (rowIndex == 0 || rowIndex == input.lastIndex) return@forEachIndexed
            s.forEachIndexed2 { colIndex, c ->
                if (colIndex == 0 || colIndex == input.lastIndex) return@forEachIndexed2

                // calculate left score
                val leftSublist = s.substring(0, colIndex).reversed()
                val leftScore = (leftSublist.indexOfFirst { it >= c }.takeIf { it != -1 } ?: leftSublist.lastIndex) + 1
                // calculate right score
                val rightSublist = s.substring(colIndex + 1, s.length)
                val rightScore = (rightSublist.indexOfFirst { it >= c }.takeIf { it != -1 } ?: rightSublist.lastIndex) + 1
                // calculate top score
                val topSublist =  topBottom[colIndex].substring(0, rowIndex).reversed()
                val topScore = (topSublist.indexOfFirst { it >= c }.takeIf { it != -1 } ?: topSublist.lastIndex) + 1
                // calculate bottom score
                val bottomSublist =  topBottom[colIndex].substring(rowIndex + 1, topBottom[colIndex].length)
                val bottomScore = (bottomSublist.indexOfFirst { it >= c }.takeIf { it != -1 } ?: bottomSublist.lastIndex) + 1

                scenicScores.add(leftScore * rightScore * topScore * bottomScore)
            }
        }
        return scenicScores.max()
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}