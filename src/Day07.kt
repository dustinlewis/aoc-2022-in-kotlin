fun main() {
    fun part1(input: List<String>): Int {
        val maxDirectorySize = 100000
        val root = parseDirectoryTree(input)
        return root.dirs.filter { it.dirSize < maxDirectorySize }.sumOf { it.dirSize }
    }

    fun part2(input: List<String>): Int {
        val maxSystemSize = 70000000
        val spaceNeeded = 30000000
        val root = parseDirectoryTree(input)
        val unusedSpace = maxSystemSize - root.dirSize
        return root.dirs.filter { it.dirSize + unusedSpace >= spaceNeeded }.minOf { it.dirSize }
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

data class Node(
    val name: String,
    val size: Int = 0,
    val isDir: Boolean = false,
    val nodes: MutableList<Node> = mutableListOf(),
    val parent: Node? = null
) {
    val dirSize: Int
        get() = size.plus(nodes.sumOf { it.dirSize })

    val dirs: List<Node>
        get() = if(isDir) listOf(this) + nodes.flatMap { it.dirs } else emptyList()
}

fun parseDirectoryTree(input: List<String>): Node {
    val root = Node(name = "/", isDir = true)
    var currentDir: Node? = root
    input.drop(1).forEach { command ->
        when {
            command.contains("$ cd ") -> {
                val dir = command.split("$ cd ")[1]
                currentDir = if (dir == "..") {
                    currentDir?.parent
                } else {
                    currentDir?.nodes?.find {
                        it.name == dir && it.isDir
                    }
                }
            }
            command.contains("dir ") -> {
                val dir = command.split("dir ")[1]
                currentDir?.nodes?.add(Node(name = dir, isDir = true, parent = currentDir))
            }
            command.first().isDigit() -> {
                val (size, name) = command.split(" ")
                currentDir?.nodes?.add(Node(name = name, size = size.toInt(), parent = currentDir))
            }
        }
    }
    return root
}