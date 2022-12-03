fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0) {
            acc, el ->
                val (opponentLetter, myLetter) = el.split(" ")
                acc + calculateMatchScore(opponentLetter, myLetter)
        }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) {
            acc, el ->
                val (opponentLetter, myLetter) = el.split(" ")
                acc + calculateMatchScoreForDesiredOutcome(opponentLetter, myLetter)
        }
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun calculateMatchScore(opponentLetter: String, myLetter: String): Int {
    val outcome = getOutcomeForLetterCodes(opponentLetter, myLetter)
    val myChoice = getChoiceFromLetterCode(myLetter)
    return outcome.pointValue + myChoice.pointValue
}

fun calculateMatchScoreForDesiredOutcome(opponentLetter: String, myLetter: String): Int {
    val desiredOutcome = getDesiredOutcomeFromLetterCode(myLetter)
    val choice = getChoiceForOutcome(opponentLetter, desiredOutcome)
    return desiredOutcome.pointValue + choice.pointValue
}

enum class Choice(val pointValue: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);
    fun defeats(): Choice =
        when(this) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }

    fun losesTo(): Choice =
         when(this) {
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }
}

enum class MatchOutcome(val pointValue: Int) {
    LOSE(0),
    DRAW(3),
    WIN(6),
}

fun getChoiceFromLetterCode(letterCode: String): Choice =
    when(letterCode) {
        "A", "X" -> Choice.ROCK
        "B", "Y" -> Choice.PAPER
        "C", "Z" -> Choice.SCISSORS
        else -> { throw Throwable("Unknown letter code") }
    }

fun getDesiredOutcomeFromLetterCode(letterCode: String): MatchOutcome =
    when(letterCode) {
        "X" -> MatchOutcome.LOSE
        "Y" -> MatchOutcome.DRAW
        "Z" -> MatchOutcome.WIN
        else -> { throw Throwable("Unknown letter code") }
    }

fun getChoiceForOutcome(opponentLetter: String, desiredOutcome: MatchOutcome): Choice {
    val opponentChoice = getChoiceFromLetterCode(opponentLetter)
    return when(desiredOutcome) {
        MatchOutcome.DRAW -> opponentChoice
        MatchOutcome.WIN -> opponentChoice.losesTo()
        MatchOutcome.LOSE -> opponentChoice.defeats()
    }
}

fun getOutcomeForLetterCodes(opponentLetter: String, myLetter: String) : MatchOutcome {
    val opponentChoice = getChoiceFromLetterCode(opponentLetter)
    val myChoice = getChoiceFromLetterCode(myLetter)
    return when (myChoice) {
        opponentChoice -> MatchOutcome.DRAW
        opponentChoice.losesTo() -> MatchOutcome.WIN
        else -> MatchOutcome.LOSE
    }
}