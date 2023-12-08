fun main() {
    val lines = "day5.txt".readFile()
    val first = lines.first
    val seeds = first.substring(first.indexOf(" ")).split(" ").filter { it.isNotEmpty() }.map { it.toLong() }

    data class MapEntry(val ds : Long, val sr : Long, val rl : Long){
        fun containsNumber(input : Long) = input >= sr && input < (sr + rl)
        fun getDestination(input : Long) = ds + (input-sr)
    }

    fun List<MapEntry>.getMapEntry(input : Long) = this.firstOrNull { it.containsNumber(input) }


    val maps = mutableMapOf<String, List<MapEntry>>()
    var currentKey = ""
    lines.drop(2).filter { it.isNotEmpty() }.forEach {
        if(it.get(0).isLetter()){
            currentKey = it.substring(0, it.indexOf(" "))
            maps[currentKey] = listOf()
            return@forEach
        }
        val mapValues = it.split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        val destinationStart = mapValues[0]
        val sourceRangeStart = mapValues[1]
        val rangeLength = mapValues[2]
        val entry = MapEntry(destinationStart,sourceRangeStart, rangeLength)
        maps[currentKey] = maps[currentKey]!!.plus(entry)
    }
    val soilMap = maps["seed-to-soil"]
    val fertilizerMap = maps["soil-to-fertilizer"]
    val waterMap = maps["fertilizer-to-water"]
    val lightMap = maps["water-to-light"]
    val temperatureMap = maps["light-to-temperature"]
    val humidityMap = maps["temperature-to-humidity"]
    val locationMap = maps["humidity-to-location"]
    val resultPart1 = seeds.map {
        val soil = soilMap?.getMapEntry(it)?.getDestination(it) ?: it
        val fertilizer = fertilizerMap?.getMapEntry(soil)?.getDestination(soil) ?: soil
        val water = waterMap?.getMapEntry(fertilizer)?.getDestination(fertilizer) ?: fertilizer
        val light = lightMap?.getMapEntry(water)?.getDestination(water) ?: water
        val temperature = temperatureMap?.getMapEntry(light)?.getDestination(light) ?: light
        val humidity = humidityMap?.getMapEntry(temperature)?.getDestination(temperature) ?: temperature
        val location = locationMap?.getMapEntry(humidity)?.getDestination(humidity) ?: humidity
        location
    }.min()
    println("result part1: $resultPart1")


    val startTime = System.currentTimeMillis()
    val resultPart2 = seeds.windowed(2, 2).map {
        var lowest = Long.MAX_VALUE
        for(a in it.first..(it.first + it.last - 1)){
            val soil = soilMap?.getMapEntry(a)?.getDestination(a) ?: a
            val fertilizer = fertilizerMap?.getMapEntry(soil)?.getDestination(soil) ?: soil
            val water = waterMap?.getMapEntry(fertilizer)?.getDestination(fertilizer) ?: fertilizer
            val light = lightMap?.getMapEntry(water)?.getDestination(water) ?: water
            val temperature = temperatureMap?.getMapEntry(light)?.getDestination(light) ?: light
            val humidity = humidityMap?.getMapEntry(temperature)?.getDestination(temperature) ?: temperature
            val location = locationMap?.getMapEntry(humidity)?.getDestination(humidity) ?: humidity
            if(location < lowest){
                lowest = location
            }
        }
        lowest
    }.min()
    println("time: ${System.currentTimeMillis()-startTime}ms")
    println("result part2: $resultPart2")
}