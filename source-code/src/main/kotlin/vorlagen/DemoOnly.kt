@file:Suppress("unused", "FunctionName", "SpellCheckingInspection")

package app.codedojo.kata.weihnachtsgeschichte.vorlage

fun main() {
    val doggy = Dackel("braun", 2)
    val fahrstuhl = Fahrstuhl(7, 550)
    for (gewicht in 40..180 step 10) { fahrstuhl.addPerson(90)}
}

interface Hund {
    val fellfarbe: String // nur getter
    var alter: Int        // getter und setter

    fun `gib Futter`(menge: Int): Unit { // void in Java
        println("Wuff… wedel…") // system.out.println
    }
}

class Kino(val sitzplaetze: Int)

class Pkw(val maxPersonen: Int = 5)

class Fahrstuhl(val maxPersonen: Int, val maxGewicht: Int) {
    private var personen: Int = 0
    private var gesamtGewicht: Int = 0

    fun addPerson(gewicht: Int) {
        if (gesamtGewicht + gewicht > maxGewicht)
            throw IllegalArgumentException("Zulässiges Gesamtgewicht von $maxGewicht ist überschritten.")
        personen++
        gesamtGewicht += gewicht;
    }
}

class Dackel(
        override val fellfarbe: String,
        override var alter: Int = 0) : Hund {

    override fun `gib Futter`(menge: Int) {
        println("wuff…")
    }

    fun `jage Dachs`() {
        //…
    }
}


sealed class Haustier(open val alter: Int)

class Katze1(val fellfarbe: String, override val alter: Int) : Haustier(alter)
class Kaiman(val gefresseneGnus: Int, override val alter: Int) : Haustier(alter)

fun `beschreibe Haustier`(haustier: Haustier) = when (haustier) {
    is Katze1 -> println("Der Fell der Katze ist $haustier.fellfarbe.")
    is Kaiman -> print("Der Kaiman hat ${haustier.gefresseneGnus} Gnus gefressen.")
}
