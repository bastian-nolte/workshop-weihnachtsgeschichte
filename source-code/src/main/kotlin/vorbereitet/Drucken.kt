package app.codedojo.kata.weihnachtsgeschichte.vorbereitet

enum class Farbe {
    GRUEN, GELB, ROT, BRAUN
}

fun `drucke in Farbe`(color: Farbe, text: String) {
    val colorLiteral = when (color) {
        Farbe.GRUEN -> 32
        Farbe.GELB -> 33
        Farbe.ROT -> 31
        Farbe.BRAUN -> 34
    }
    println("${27.toChar()}[${colorLiteral}m$text${27.toChar()}[0m")
}
