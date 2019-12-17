@file:Suppress("unused", "FunctionName", "SpellCheckingInspection")
package app.codedojo.kata.weihnachtsgeschichte.beispiele

fun main() {
    val fahrstuhl = Fahrstuhl()
    for (gewicht: Int in 40..140 step 10) {
        fahrstuhl.`person steigt ein`(gewicht)
    }
}

class Fahrstuhl(val maxPersonen: Int = 7, val maxGewicht: Int = 600) {
    private var personen: Int = 0
    private var gesamtGewicht: Int = 0

    init {
        println("Dieser Fahrstuhl darf max. $maxPersonen Personen mit einem Gesamtgewicht von $maxGewicht Kg transprotieren.")
    }

    fun `person steigt ein`(gewicht: Int) {
        if (gesamtGewicht + gewicht > maxGewicht)
            throw IllegalArgumentException("Zulässiges Gesamtgewicht von $maxGewicht ist überschritten.")

        `erhöhe Auslaustung um eine Person`(gewicht)
    }

    private fun `erhöhe Auslaustung um eine Person`(gewicht: Int) {
        personen++
        gesamtGewicht += gewicht;
        println("Dieser Fahrstuhl transportiert zur Zeit $personen Person(en) mit einem Gesamtgewicht von $gesamtGewicht Kg.")
    }
}
