@file:Suppress("FunctionName", "unused", "SpellCheckingInspection", "NonAsciiCharacters")

/**
 * Die Lösung aus dem Exzellenzworkshop.
 */
package app.codedojo.kata.weihnachtsgeschichte

import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Fall
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Farbe
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Geschlecht
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.`drucke in Farbe`
import java.io.File

fun main() {
    with(Lustighuusen(`einwohner aus Datei lesen`(), Gruusige())) {
        `einwohner ausgeben`(einwohner)

        for (i in 1..25) {
            gruusige.`liefere Geschenk an Einwohner`(
                    fabrik.`erstelle Geschenk zufälligen Typs`(),
                    einwohner.random()
            )
        }
    }
}

fun `einwohner aus Datei lesen`(): Set<Einwohner> {
    return File("src/main/resources/vornamen.txt").useLines { vornamen ->
        vornamen.map { vorname -> Einwohner(vorname) }.toSet()
    }
}

fun `einwohner ausgeben`(einwohnerset: Set<Einwohner>) {
    println("Liste mit 1'000 typischen schweizer Namen.")
    for ((i, einwohner) in einwohnerset.withIndex()) {
        println("$i: ${einwohner.name}")
    }
    println("=".repeat(120))
    println()
}

class Lustighuusen(val einwohner: Set<Einwohner>, val gruusige: Gruusige) {
    // Aufgabe 1: Ändern in computed getter der den Durchschnitt des GuteLauneIndex ausgibt.
    var guteLauneIndex: Int = 100
        private set

    val fabrik: Fabrik = Fabrik()
}

class Einwohner(val name: String) {
    var guteLauneIndex: Int = 100
        private set

    fun `nehme Geschenk an`(geschenk: Geschenk) {
        `drucke in Farbe`(Farbe.GELB, "$name nimmt ${geschenk.name} entgegen 🥰")
        `spiele mit Geschenk`(geschenk)
    }

    private fun `spiele mit Geschenk`(geschenk: Geschenk) =
            when (geschenk) {
                is T100 -> `drucke in Farbe`(Farbe.ROT, "$name wird vom T100 gepeinigt 🤬🤯")
                is BenutzteWindel -> `drucke in Farbe`(Farbe.ROT, "$name muss sich übergeben 🤮")
                is VerrueckterDueuetscher -> `drucke in Farbe`(Farbe.ROT, "$name wird sehr unfründlich genervt! Alle ussschaffe! 🚔")
                is SalzigerSchokkiKuss -> `drucke in Farbe`(Farbe.ROT, "$name zieht sich alles im Mund zusammen 🤢")
                is DefektesFahrrad -> `drucke in Farbe`(Farbe.ROT, "$name fährt gegen das Auto des Nachbarn. Das wird teuer! 🚲 🤕")
            }
}

class Gruusige {
    fun `liefere Geschenk an Einwohner`(geschenk: Geschenk, einwohner: Einwohner) {
        `drucke in Farbe`(Farbe.GRUEN, "Der Gruusige liefert ${geschenk.geschlecht.unbestimmterArtikel(Fall.AKKUSATIV)} ${geschenk.beschreibung} an ${einwohner.name} aus 🥶.")
        einwohner.`nehme Geschenk an`(geschenk)
    }
}

class Fabrik {
    private val geschenkeMaschine: Set<() -> Geschenk> = setOf(
            { T100() },
            { BenutzteWindel() },
            { VerrueckterDueuetscher() },
            { SalzigerSchokkiKuss() },
            { DefektesFahrrad() }
    )

    fun `erstelle Geschenk zufälligen Typs`() = geschenkeMaschine.random().invoke()
}


sealed class Geschenk(val name: String, val geschlecht: Geschlecht, val beschreibung: String, val stimmungspunkte: Int)

class T100 : Geschenk("T100", Geschlecht.MÄNNLICH, "Roboter mit feindlicher Einstellung", 80)
class BenutzteWindel : Geschenk("benutzte Windel", Geschlecht.WEIBLICH, "Windel die sehr stark stinkt", 30)
class VerrueckterDueuetscher : Geschenk("verrückten Düütschen", Geschlecht.MÄNNLICH, "Menschen mit germanischen Wurzeln der echt verrückt ist", 45)
class SalzigerSchokkiKuss : Geschenk("sehr salzigen Schokkikuss", Geschlecht.MÄNNLICH, "Schokkikuss der ganz ekelig schmeckt", 25)
class DefektesFahrrad : Geschenk("defektes Rad", Geschlecht.SACHLICH, "Fahrrad dessen Bremse nicht immer funktioniert", 60)
