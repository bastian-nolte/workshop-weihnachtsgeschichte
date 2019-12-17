@file:Suppress("unused", "PackageDirectoryMismatch", "UNUSED_VARIABLE")

package app.codedojo.kata.weihnachtsgeschichte.vorlagen.s4

import java.io.File

fun main() {
    val gruusige = Gruusige()
    // Neues >>
    val einwohner = `einwohner aus Datei lesen`()
    `einwohner ausgeben`(einwohner)
    val lustighuusen = Lustighuusen(einwohner, gruusige)
}

fun `einwohner aus Datei lesen`(): Set<Einwohner> {
    return File("src/main/resources/vornamen.txt").useLines {
        it.map { name -> Einwohner(name) }.toSet()
    }
}

fun `einwohner ausgeben`(einwohner: Set<Einwohner>) {
    for ((index, einwohner) in einwohner.withIndex()) {
        println("Einwohner ${index + 1}: ${einwohner.name}")
    }
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
