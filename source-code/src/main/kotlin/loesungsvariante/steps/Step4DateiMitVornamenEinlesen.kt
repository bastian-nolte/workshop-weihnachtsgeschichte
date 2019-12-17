@file:Suppress("unused", "PackageDirectoryMismatch", "UNUSED_VARIABLE")

package app.codedojo.kata.weihnachtsgeschichte.vorlagen.s4

import java.io.File

fun main() {
    val gruusige = Gruusige()
    val lustighuusen = Lustighuusen(`einwohner aus Datei lesen`(), gruusige)
}

// Neues >>
fun `einwohner aus Datei lesen`(): Set<Einwohner> {
    // Einlesen
    val einwohner = File("src/main/resources/vornamen.txt").useLines {
        it.map { name -> Einwohner(name) }.toSet()
    }

    // Ausgeben
    for ((index, einwohner) in einwohner.withIndex()) {
        println("Einwohner ${index + 1}: ${einwohner.name}")
    }

    return einwohner
}
// << oeNeues

class Lustighuusen(val einwohner: Set<Einwohner>, val gruusige: Gruusige) {
    var guteLauneIndex: Int = 100
        private set
}

class Einwohner(val name: String) {
    var guteLauneIndex: Int = 100
        private set
}

class Gruusige
