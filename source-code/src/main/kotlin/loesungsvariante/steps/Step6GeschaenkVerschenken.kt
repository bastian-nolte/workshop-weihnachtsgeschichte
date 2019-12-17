@file:Suppress("unused", "PackageDirectoryMismatch", "UNUSED_VARIABLE")

package app.codedojo.kata.weihnachtsgeschichte.vorlagen.s6

import app.codedojo.kata.weihnachtsgeschichte.loesungsvariante.MitBeschreibung
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Geschlecht

fun main() {
    val gruusige = Gruusige()
    val einwohner = Einwohner("Herbert")
    val lustighuusen = Lustighuusen(setOf(einwohner), gruusige)

    // Neues >>

    // oeNeues
}

class Lustighuusen(val einwohner: Set<Einwohner>, val gruusige: Gruusige) {
    var guteLauneIndex: Int = 100
        private set
}

class Einwohner(val name: String) {
    var guteLauneIndex: Int = 100
        private set
}

class Gruusige

sealed class Geschenk(
        override val name: String,
        val geschlecht: Geschlecht,
        override val beschreibung: String,
        val stimmungspunkte: Int) : MitBeschreibung

class Fahrrad : Geschenk(
        name = "Fahrrad",
        geschlecht = Geschlecht.SACHLICH,
        beschreibung = "Fahrrad dessen Bremse ab und zu ausfällt",
        stimmungspunkte = 70)

class Bonbon : Geschenk(
        name = "Bonbon",
        geschlecht = Geschlecht.SACHLICH,
        beschreibung = "Bonbon mit sehr scharfer Füllung",
        stimmungspunkte = 20)

class Kuscheltier : Geschenk(
        name = "Kuscheltier",
        geschlecht = Geschlecht.SACHLICH,
        beschreibung = "Kuscheltier mit Flöhen im Fell",
        stimmungspunkte = 50)

class Katze : Geschenk(
        name = "Katze",
        geschlecht = Geschlecht.WEIBLICH,
        beschreibung = "sehr bissige Katze",
        stimmungspunkte = 45)
