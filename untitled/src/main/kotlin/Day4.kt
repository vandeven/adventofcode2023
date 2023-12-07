import kotlin.math.pow

fun main() {

    val readFile = "day4.txt".readFile()
    val resultPart1 = readFile.map {
        val ticketNumbers = it.substring(it.indexOf(":") + 1, it.indexOf("|") -1).trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val winningNumbers = it.substring(it.indexOf("|")+ 1).trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt()}
        val size = ticketNumbers.intersect(winningNumbers.toSet()).size
        if(size == 0) {
            0
        } else {
            2.0.pow(size.toDouble() - 1).toInt()
        }
    }.sum()

    println("result part 1: $resultPart1")

    val gamesMap = readFile.map {
        val gameId = it.substring(0, it.indexOf(":")).substring(it.indexOf(" ") + 1, it.indexOf(":")).trim().toInt()
        val ticketNumbers = it.substring(it.indexOf(":") + 1, it.indexOf("|") -1).trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val winningNumbers = it.substring(it.indexOf("|")+ 1).trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt()}
        Pair(gameId, ticketNumbers.intersect(winningNumbers.toSet()).size)
    }.fold(mapOf<Int, Pair<Int, Int>>()) { acc, v ->
        acc.plus(Pair(v.first, Pair(v.second, 1)))
    }.toMutableMap()

    gamesMap.forEach{ e ->
            for(i in 1..e.value.first) {
                if(gamesMap.size > e.key + 1){
                    val higherGameIndex = e.key + i
                    gamesMap[higherGameIndex] = gamesMap[higherGameIndex]!!.copy(second = gamesMap[higherGameIndex]!!.second + e.value.second)
                }
            }
        }
    val resultPart2 = gamesMap.map { it.value.second }.sum()
    println("result part 2: $resultPart2")
}