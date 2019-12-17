@file:Suppress("CanBeParameter")

package app.codedojo.kata.weihnachtsgeschichte.livecodings

fun main() {
    val fahrstuhl = Fahrstuhl(anzahlFenster = null)
    for (gewicht: Int in 40..140 step 10) {
        fahrstuhl.`person steigt ein`(gewicht)
    }
}

class Fahrstuhl(val maxPersonen: Int = 7, val maxGewicht: Int = 600, anzahlFenster: Int?) {
    private var personen: Int = 0
    private var gesamtGewicht: Int = 0

    init {
        println("Dieser Fahrstuhl darf max. $maxPersonen Personen mit einem Gesamtgewicht von $maxGewicht Kg transprotieren.")
        println("Dieser Fahrstuhl hat weniger als ${anzahlFenster?.plus(1)} Fenster.")
        println("Dieser Fahrstuhl hat weniger als ${anzahlFenster?:0.plus(1)} Fenster.")
    }

    fun `person steigt ein`(gewicht: Int) {
        if (gesamtGewicht + gewicht > maxGewicht)
            throw IllegalArgumentException("Zulässiges Gesamtgewicht von $maxGewicht ist überschritten.")

        `erhoehe Auslaustung um eine Person`(gewicht)
    }

    private fun `erhoehe Auslaustung um eine Person`(gewicht: Int) {
        personen++
        gesamtGewicht += gewicht
        println("Dieser Fahrstuhl transportiert zur Zeit $personen Person(en) mit einem Gesamtgewicht von $gesamtGewicht Kg.")
    }
}
