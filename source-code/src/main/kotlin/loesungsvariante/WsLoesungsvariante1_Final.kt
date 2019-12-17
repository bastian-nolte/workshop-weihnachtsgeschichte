@file:Suppress("NonAsciiCharacters", "FunctionName", "unused")

package app.codedojo.kata.weihnachtsgeschichte.loesungsvariante

import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Fall
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Farbe
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Geschlecht
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.`drucke in Farbe`
import io.reactivex.Emitter
import io.reactivex.Observable
import java.util.concurrent.CompletableFuture
import kotlin.properties.Delegates
import kotlin.random.Random


fun main() {
    val lustighausen = Lustighausen()
    var produktionAbgeschlossen = false

    lustighausen.fabrik.produzierteGeschenkeRxObservable
            .subscribe(
                    { neuesGeschenk -> lustighausen.grinch.`liefere Geschenk an Einwohner`(neuesGeschenk, lustighausen.`ein zufälliger Einwohner`()) },
                    {},
                    { produktionAbgeschlossen = true }
            )

    lustighausen.fabrik.produziereGeschenke(anzahl = 15)

    while (!produktionAbgeschlossen) {
        // Never ends
        Thread.sleep(100)
    }
}

class Lustighausen {
    val fabrik = Fabrik()
    val grinch = Grinch()
    private val einwohner = setOf(Einwohner("Heribert"), Einwohner("Giselinde"), Einwohner("Annabelle"))

    fun `ein zufälliger Einwohner`() = einwohner.random()
}

class Grinch {
    fun `liefere Geschenk an Einwohner`(geschenk: Geschenk, einwohner: Einwohner) {
        `drucke in Farbe`(Farbe.GRUEN, "Der Grinch liefert ${geschenk.geschlecht.unbestimmterArtikel(Fall.AKKUSATIV)} ${geschenk.beschreibung} an ${einwohner.name}. 🥶")
        einwohner.`nehme Geschenk an`(geschenk)
    }
}

class Einwohner(val name: String) {
    fun `nehme Geschenk an`(geschenk: Geschenk) {
        `drucke in Farbe`(Farbe.GELB, "${this.name} nimmt das ${geschenk.name} entgegen. 🥰")
        `spiele mit Geschenk`(geschenk)
    }

    private fun `spiele mit Geschenk`(geschenk: Geschenk) {
        when (geschenk) {
            is Fahrrad -> `drucke in Farbe`(Farbe.ROT, "$name fährt schreiend gegen Auto vom Nachbarn. 😡")
            is Bonbon -> `drucke in Farbe`(Farbe.ROT, "$name schnieft wegen der Schärfe. 🤧")
            is Kuscheltier -> `drucke in Farbe`(Farbe.ROT, "$name krazt sich die Haut auf. 🤬")
            is Katze -> `drucke in Farbe`(Farbe.ROT, "$name wird vom Arzt genäht. ")
        }
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

interface MitBeschreibung {
    val name: String
    val beschreibung: String
}

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
