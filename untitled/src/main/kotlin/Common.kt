class Common {
}

fun String.readFile() = Common::class.java.getResource(this)!!.readText().lines()