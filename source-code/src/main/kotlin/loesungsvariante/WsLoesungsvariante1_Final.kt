@file:Suppress("NonAsciiCharacters", "FunctionName", "unused", "DuplicatedCode")

package app.codedojo.kata.weihnachtsgeschichte.loesungsvariante

import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Fall
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Farbe
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Geschlecht
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.`drucke in Farbe`
import io.reactivex.Emitter
import io.reactivex.Observable
import java.io.File
import java.util.concurrent.CompletableFuture
import kotlin.properties.Delegates
import kotlin.random.Random


fun main() {
    val lustighausen = Lustighuusen(`einwohner aus Datei lesen`(), Gruusige())
    var produktionAbgeschlossen = false

    lustighausen.fabrik.produzierteGeschenkeRxObservable
            .subscribe(
                    { neuesGeschenk -> lustighausen.gruusige.`liefere Geschenk an Einwohner`(neuesGeschenk, lustighausen.`ein zufälliger Einwohner`()) },
                    {},
                    { produktionAbgeschlossen = true }
            )

    lustighausen.fabrik.produziereGeschenke(anzahl = 15)

    while (!produktionAbgeschlossen) {
        // Never ends
        Thread.sleep(100)
    }
}

fun `einwohner aus Datei lesen`(): Set<Einwohner> {
    return File("src/main/resources/vornamen.txt").useLines {
        it.map { name -> Einwohner(name) }.toSet()
    }
}


class Lustighuusen(val einwohner: Set<Einwohner>, val gruusige: Gruusige) {
    var guteLauneIndex: Int = 100
        private set

    val fabrik = Fabrik()
    fun `ein zufälliger Einwohner`() = einwohner.random()
}

class Einwohner(val name: String) {
    var guteLauneIndex: Int = 100
        private set

    fun `nehme Geschenk an`(geschenk: Geschenk) {
        `drucke in Farbe`(Farbe.GELB, "${this.name} nimmt ${geschenk.name} entgegen. 🥰")
        `spiele mit Geschenk`(geschenk)
    }

    private fun `spiele mit Geschenk`(geschenk: Geschenk) {
        when (geschenk) {
            is Fahrrad -> `drucke in Farbe`(Farbe.ROT, "$name fährt schreiend gegen Auto vom Nachbarn. 😡")
            is Bonbon -> `drucke in Farbe`(Farbe.ROT, "$name schnieft wegen der Schärfe. 🤧")
            is Kuscheltier -> `drucke in Farbe`(Farbe.ROT, "$name kratzt sich die Haut auf. 🤬")
            is Katze -> `drucke in Farbe`(Farbe.ROT, "$name wird vom Arzt genäht. ☠️")
        }
    }
}

class Gruusige {
    fun `liefere Geschenk an Einwohner`(geschenk: Geschenk, einwohner: Einwohner) {
        `drucke in Farbe`(Farbe.GRUEN, "Der Grinch liefert ${geschenk.geschlecht.unbestimmterArtikel(Fall.AKKUSATIV)} ${geschenk.beschreibung} an ${einwohner.name}. 🥶")
        einwohner.`nehme Geschenk an`(geschenk)
    }
}


class Fabrik {
    /**
     * Ein Set von Funktionen die bei Aufruf jeweils eine andere Instanz eines Geschenkes liefern.
     */
    private val geschenkeMaschine: Set<() -> Geschenk> = setOf({ Fahrrad() }, { Bonbon() }, { Kuscheltier() }, { Katze() })

    /**
     * Liefert ein Geschenk eines zufälligen Typs.
     */
    private fun `erstelle Geschenk zufälligen Typs`() = geschenkeMaschine.random().invoke()

    /**
     * Ein Geschenk-Emmitter
     */
    private lateinit var emitter: Emitter<Geschenk>

    /**
     * Delegierte Eigenschaft mit der Information, wie viele Einheiten noch zu produzieren sind.
     */
    private var nochZuProduzieren: Int by Delegates.observable(0) { _, _, new ->
        if (new == 0) `informiere darüber, dass die Produktion nun abgeschlossen ist`()
    }


    val produzierteGeschenkeRxObservable = Observable.create<Geschenk> { this.emitter = it }

    /**
     * Produziert die übergebenen Anzahl von Geschenken mit einer Zeitverzögerung von 0.1 bis eine Sekunde pro Geschenk.
     * @param anzahl Die Anzahl der zu produzierenden Geschenke.
     */
    fun produziereGeschenke(anzahl: Int) {
        nochZuProduzieren += anzahl
        repeat(anzahl) { produziereGeschenk() }
    }

    /**
     * Wartet zwischen 0.1 und einer Sekunde und produziert dann ein Geschenk eines zufälligen Typs.
     */
    private fun produziereGeschenk(): CompletableFuture<Unit> = CompletableFuture.supplyAsync {
        Thread.sleep(Random.nextLong(100, 1000))
        val geschenk = `erstelle Geschenk zufälligen Typs`()
        `informiere über neu erstelltes Geschenk`(geschenk)
        nochZuProduzieren -= 1
    }

    private fun `informiere über neu erstelltes Geschenk`(geschenk: Geschenk) = emitter.onNext(geschenk)

    private fun `informiere darüber, dass die Produktion nun abgeschlossen ist`() = emitter.onComplete()
}

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
