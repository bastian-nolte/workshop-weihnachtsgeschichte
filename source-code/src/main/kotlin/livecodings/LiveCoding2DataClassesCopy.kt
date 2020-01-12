@file:Suppress("PackageDirectoryMismatch")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.c2

fun main() {
    var hans = GoldenRetriever("braun", 2)
    println("Das Fell von Hans ist ${hans.fellfarbe} und Hans ist ${hans.alter} Jahre alt.")

    hans = hans.copy(alter = hans.alter + 1)
    println("Das Fell von Hans ist ${hans.fellfarbe} und Hans ist ${hans.alter} Jahre alt.")

    hans = hans.copy(alter = hans.alter + 10, fellfarbe = "braun-grau")
    println("Das Fell von Hans ist ${hans.fellfarbe} und Hans ist ${hans.alter} Jahre alt.")
}

data class GoldenRetriever(
        val fellfarbe: String,
        val alter: Int)
