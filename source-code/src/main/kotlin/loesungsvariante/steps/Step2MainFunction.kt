@file:Suppress("unused", "PackageDirectoryMismatch", "UNUSED_VARIABLE")
package app.codedojo.kata.weihnachtsgeschichte.vorlagen.s2

// Neues >>
fun main(){
    val gruusige = Gruusige()
    val einwohner = Einwohner("Herbert")
    val lustighuusen = Lustighuusen(setOf(einwohner), gruusige)
}
// << oeNeues

class Lustighuusen(val einwohner: Set<Einwohner>, val gruusige: Gruusige, var guteLauneIndex: Int = 100)

class Einwohner(val name: String, var guteLauneIndex: Int = 100)

class Gruusige
