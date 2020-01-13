@file:Suppress("unused", "PackageDirectoryMismatch", "UNUSED_VARIABLE")

package app.codedojo.kata.weihnachtsgeschichte.vorlagen.s3

fun main() {
    val gruusige = Gruusige()
    val einwohner = Einwohner("Herbert")
    val lustighuusen = Lustighuusen(setOf(einwohner), gruusige)
}

class Lustighuusen(val einwohner: Set<Einwohner>, val gruusige: Gruusige) {
    // Neues >>
    var guteLauneIndex: Int = 100
        private set
    // << Neues
}

class Einwohner(val name: String) {
    // Neues >>
    var guteLauneIndex: Int = 100
        private set
    // << Neues
}

class Gruusige
