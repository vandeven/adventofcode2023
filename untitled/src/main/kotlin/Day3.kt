

data class Coordinate(val x : Int, val y : Int)
sealed interface GridEntry {
    data class Number(val number : String, val points : List<Coordinate>) : GridEntry
    data class Symbol(val symbol : String, val point : Coordinate) : GridEntry
}
fun main() {

    var x = 0
    var y = 0
    var gridAcc = listOf<GridEntry>()
    for(line in "/day3.txt".readFile()){
        var numberAcc = GridEntry.Number("", listOf())
        for(c in line.toCharArray()){
            if(c.isDigit()){
                numberAcc = GridEntry.Number(numberAcc.number + c.toString(), numberAcc.points.plus(Coordinate(x,y)))
                if(y == line.length -1){
                    gridAcc = gridAcc.plus(numberAcc)
                    numberAcc = GridEntry.Number("", listOf())
                }
            } else if(c == '.'){
                if(numberAcc.number.length != 0){
                    gridAcc = gridAcc.plus(numberAcc)
                    numberAcc = GridEntry.Number("", listOf())
                }
            } else {
                if(numberAcc.number.length != 0){
                    gridAcc = gridAcc.plus(numberAcc)
                    numberAcc = GridEntry.Number("", listOf())
                }
                gridAcc = gridAcc.plus(GridEntry.Symbol(c.toString(), Coordinate(x, y)))
            }
            y++
        }
        y = 0
        x++
    }
    val symbolPoints = gridAcc.filterIsInstance<GridEntry.Symbol>().map { it.point }
    val resultPart1 = gridAcc.filterIsInstance<GridEntry.Number>()
        .filterNot {
            it.points.none {
                symbolPoints.contains(it.copy(x = it.x + 1)) || symbolPoints.contains(it.copy(x = it.x - 1)) ||
                        symbolPoints.contains(it.copy(y = it.y + 1)) || symbolPoints.contains(it.copy(y = it.y - 1)) ||
                        symbolPoints.contains(
                            it.copy(
                                x = it.x + 1,
                                y = it.y + 1
                            )
                        ) || symbolPoints.contains(it.copy(x = it.x - 1, y = it.y - 1)) ||
                        symbolPoints.contains(
                            it.copy(
                                x = it.x - 1,
                                y = it.y + 1
                            )
                        ) || symbolPoints.contains(it.copy(x = it.x + 1, y = it.y - 1))
            }
        }.sumOf { it.number.toInt() }
    println("result part 1: $resultPart1")

    val resultPart2 = gridAcc.filterIsInstance<GridEntry.Symbol>()
        .filter { it.symbol == "*" }
        .sumOf { s ->
            val numbers = gridAcc.filterIsInstance<GridEntry.Number>().filter {
                it.points.contains(s.point.copy(x = s.point.x + 1)) || it.points.contains(s.point.copy(x = s.point.x - 1)) ||
                        it.points.contains(s.point.copy(y = s.point.y + 1)) || it.points.contains(s.point.copy(y = s.point.y - 1)) ||
                        it.points.contains(
                            s.point.copy(
                                x = s.point.x + 1,
                                y = s.point.y + 1
                            )
                        ) || it.points.contains(s.point.copy(x = s.point.x - 1, y = s.point.y - 1)) ||
                        it.points.contains(
                            s.point.copy(
                                x = s.point.x - 1,
                                y = s.point.y + 1
                            )
                        ) || it.points.contains(s.point.copy(x = s.point.x + 1, y = s.point.y - 1))
            }
            if (numbers.size == 2) {
                numbers.map { it.number.toInt() }.reduce { a, b -> a * b }
            } else {
                0
            }
        }
    println("result part 2: $resultPart2")
}