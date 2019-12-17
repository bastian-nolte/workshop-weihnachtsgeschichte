@file:Suppress("FunctionName")

package app.codedojo.kata.weihnachtsgeschichte.livecodings

fun main() {
    `beschreibe Haustier`(KatzeA("blau", 3))
    `beschreibe Haustier`(KaimanA(74, 7))
    `beschreibe Haustier`(Dackel(12))
}

sealed class Haustier(open val alter: Int)

class KatzeA(val fellfarbe: String, alter: Int) : Haustier(alter)
class KaimanA(val gefresseneGnus: Int, alter: Int) : Haustier(alter)
class Dackel(alter: Int) : Haustier(alter)

fun `beschreibe Haustier`(haustier: Haustier) = when (haustier) {
    is KatzeA -> println("Der Fell der Katze ist ${haustier.fellfarbe}.")
    is KaimanA -> println("Der Kaiman hat ${haustier.gefresseneGnus} Gnus gefressen.")
    is Dackel -> println("Der Dackel ist ${haustier.alter} Jahr(e) alt.")
}
