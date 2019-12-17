@file:Suppress("unused", "PackageDirectoryMismatch")
package app.codedojo.kata.weihnachtsgeschichte.vorlagen.s1

class Lustighuusen(val einwohner: Set<Einwohner>, val gruusige: Gruusige, var guteLauneIndex: Int = 100)

class Einwohner(val name: String, var guteLauneIndex: Int = 100)

class Gruusige
