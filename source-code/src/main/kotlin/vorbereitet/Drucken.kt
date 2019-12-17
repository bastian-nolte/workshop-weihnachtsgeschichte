package app.codedojo.kata.weihnachtsgeschichte.vorbereitet

enum class Farbe {
    GRUEN, GELB, ROT
}

fun `drucke in Farbe`(color: Farbe, text: String) {
    val colorLiteral = when (color) {
        Farbe.GRUEN -> 32
        Farbe.GELB -> 33
        Farbe.ROT -> 31
    }
    println("${27.toChar()}[${colorLiteral}m$text")
}
