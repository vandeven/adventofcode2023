fun main() {
    data class Cube(val colour: String, val amount : Int)

    val games = "/day2.txt".readFile().map {
        val index = it.substring(5, it.indexOf(":"))
        val game = it.substring(it.indexOf(":") + 1)
        val games = game.split(";")
            .map { it.trim() }
            .map {
                it.split(",")
                    .map { it.trim() }
                    .map {
                        val amount = it.substring(0, it.indexOf(" "))
                        val colour = it.substring(it.indexOf(" ") + 1)
                        Cube(colour, amount.toInt())
                    }
            }
        Pair(index, games)
    }

    val resultPart1 = games.filter {
        val cubes = it.second.flatMap { it }.groupBy { it.colour }
        if (cubes["red"]?.any { it.amount > 12 } == true) {
            false
        } else if (cubes["green"]?.any { it.amount > 13 } == true) {
            false
        } else if (cubes["blue"]?.any { it.amount > 14 } == true) {
            false
        } else {
            true
        }
    }.sumOf { it.first.toInt() }
    println("result part 1: $resultPart1")

    val resultPart2 =games.map {
        val cubes = it.second.flatMap { it }.groupBy { it.colour }
        val maxRed = cubes["red"]?.maxBy { it.amount }?.amount ?: 0
        val maxGreen = cubes["green"]?.maxBy { it.amount }?.amount ?: 0
        val maxBlue = cubes["blue"]?.maxBy { it.amount }?.amount ?: 0
        maxRed * maxGreen * maxBlue
    }.sum()
    println("result part 2: $resultPart2")
}