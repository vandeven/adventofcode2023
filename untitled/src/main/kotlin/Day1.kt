fun main() {
    val resultPart1 = "/day1.txt".readFile()
        .asSequence()
        .map { it.replace("\\D+".toRegex(), "") }
        .filter { it.isNotEmpty() }
        .map { it.toCharArray() }.map { "${it.first()}${it.last()}" }
        .map { Integer.valueOf(it) }
        .sum()
    println("result part 1: $resultPart1")

    val translation = listOf(
        Pair(".*one\$".toRegex(),"1"),
        Pair(".*two\$".toRegex(),"2"),
        Pair(".*three\$".toRegex(),"3"),
        Pair(".*four\$".toRegex(),"4"),
        Pair(".*five\$".toRegex(),"5"),
        Pair(".*six\$".toRegex(),"6"),
        Pair(".*seven\$".toRegex(),"7"),
        Pair(".*eight\$".toRegex(),"8"),
        Pair(".*nine\$".toRegex(),"9")
    )

    val resultPart2 = "/day1.txt".readFile()
        .asSequence()
        .map {
            it.fold("") { acc, c ->
                if (c.isDigit()) {
                    "$acc$c"
                } else {
                    val add = "$acc$c"
                    val match = translation.find { it.first.matches(add) }
                    if (match != null) {
                        "$acc${match.second}$c"
                    } else {
                        add
                    }
                }
            }
        }
        .map { it.replace("\\D+".toRegex(), "") }
        .map { it.toCharArray() }.map { "${it.first()}${it.last()}" }
        .map { Integer.valueOf(it) }
        .sum()
    println("result part 2: $resultPart2")
}