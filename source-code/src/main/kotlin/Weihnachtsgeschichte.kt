@file:Suppress("NonAsciiCharacters")
package app.codedojo.kata.weihnachtsgeschichte

import app.codedojo.kata.weihnachtsgeschichte.vorlage.Geschlecht
import app.codedojo.kata.weihnachtsgeschichte.vorlage.MitBeschreibung

fun main(arguments: Array<String>) {
}




sealed class Geschenk(
        override val name: String,
        val geschlecht: Geschlecht,
        override val beschreibung: String) : MitBeschreibung

class Fahrrad : Geschenk(
        name = "Fahrrad",
        geschlecht = Geschlecht.SACHLICH,
        beschreibung = "Fahrrad dessen Bremse ab und zu ausfällt")

class Bonbon : Geschenk(
        name = "Bonbon",
        geschlecht = Geschlecht.SACHLICH,
        beschreibung = "Bonbon mit sehr scharfer Füllung")
