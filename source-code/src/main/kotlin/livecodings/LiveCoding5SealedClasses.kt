@file:Suppress("FunctionName", "PackageDirectoryMismatch")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.c5

fun main() {
    `beschreibe Haustier`(Katze("blau", 3))
    `beschreibe Haustier`(Kaiman(74, 7))
//    `beschreibe Haustier`(Dackel(12))
}

sealed class Haustier(open val alter: Int)

class Katze(val fellfarbe: String, alter: Int) : Haustier(alter)
class Kaiman(val gefresseneGnus: Int, alter: Int) : Haustier(alter)

fun `beschreibe Haustier`(haustier: Haustier) = when (haustier) {
    is Katze -> println("Der Fell der Katze ist ${haustier.fellfarbe}.")
    is Kaiman -> println("Der Kaiman hat ${haustier.gefresseneGnus} Gnus gefressen.")
}
