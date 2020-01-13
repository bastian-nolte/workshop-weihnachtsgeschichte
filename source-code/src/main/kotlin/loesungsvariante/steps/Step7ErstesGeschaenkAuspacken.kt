@file:Suppress("unused", "PackageDirectoryMismatch", "UNUSED_VARIABLE", "FunctionName", "DuplicatedCode")

package app.codedojo.kata.weihnachtsgeschichte.vorlagen.s7

import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Farbe
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Geschlecht
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.`drucke in Farbe`
import java.io.File

fun main() {
    val gruusige = Gruusige()
    val lustighuusen = Lustighuusen(`einwohner aus Datei lesen`(), gruusige)

    // Neues 2 >>
    lustighuusen.einwohner.random().`nehme Geschenk an`(Kuscheltier())
    // oeNeues
}

fun `einwohner aus Datei lesen`(): Set<Einwohner> {
    return File("src/main/resources/vornamen.txt").useLines {
        it.map { name -> Einwohner(name) }.toSet()
    }
}

class Lustighuusen(val einwohner: Set<Einwohner>, val gruusige: Gruusige) {
    var guteLauneIndex: Int = 100
        private set
}

class Einwohner(val name: String) {
    var guteLauneIndex: Int = 100
        private set

    fun `nehme Geschenk an`(geschenk: Geschenk) {
        `drucke in Farbe`(Farbe.GELB, "${this.name} nimmt ${geschenk.name} entgegen. 🥰")
        `spiele mit Geschenk`(geschenk)
    }

    // Neues >>
    private fun `spiele mit Geschenk`(geschenk: Geschenk) {
        when (geschenk) {
            is Fahrrad -> `drucke in Farbe`(Farbe.ROT, "$name fährt schreiend gegen Auto vom Nachbarn. 😡")
            is Bonbon -> `drucke in Farbe`(Farbe.ROT, "$name schnieft wegen der Schärfe. 🤧")
            is Kuscheltier -> `drucke in Farbe`(Farbe.ROT, "$name kratzt sich die Haut auf. 🤬")
            is Katze -> `drucke in Farbe`(Farbe.ROT, "$name wird vom Arzt genäht. ☠️")
        }
    }
    // << eoNeues
}

class Gruusige

sealed class Geschenk(
        val name: String,
        val geschlecht: Geschlecht,
        val beschreibung: String,
        val stimmungspunkte: Int)

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
