@file:Suppress("NonAsciiCharacters")

package app.codedojo.kata.weihnachtsgeschichte.vorlage

import app.codedojo.kata.weihnachtsgeschichte.vorlage.Fall.*
import io.reactivex.Emitter
import io.reactivex.Observable
import java.util.concurrent.CompletableFuture
import kotlin.properties.Delegates
import kotlin.random.Random


enum class Color {
    GRUEN, GELB, ROT
}

fun `drucke in Farbe`(color: Color, text: String) {
    val colorLiteral = when (color) {
        Color.GRUEN -> 32
        Color.GELB -> 33
        Color.ROT -> 31
    }
    println("${27.toChar()}[${colorLiteral}m$text")
}

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
        `drucke in Farbe`(Color.GRUEN, "Der Grinch liefert ${geschenk.geschlecht.unbestimmterArtikel(AKKUSATIV)} ${geschenk.beschreibung} an ${einwohner.name}. 🥶")
        einwohner.`nehme Geschenk an`(geschenk)
    }
}

class Einwohner(val name: String) {
    fun `nehme Geschenk an`(geschenk: Geschenk) {
        `drucke in Farbe`(Color.GELB, "${this.name} nimmt das ${geschenk.name} entgegen. 🥰")
        `spiele mit Geschenk`(geschenk)
    }

    private fun `spiele mit Geschenk`(geschenk: Geschenk) {
        when (geschenk) {
            is Fahrrad -> `drucke in Farbe`(Color.ROT, "$name fährt schreiend gegen Auto vom Nachbarn. 😡")
            is Bonbon -> `drucke in Farbe`(Color.ROT, "$name schnieft wegen der Schärfe. 🤧")
            is Kuscheltier -> `drucke in Farbe`(Color.ROT, "$name krazt sich die Haut auf. 🤬")
            is Katze -> `drucke in Farbe`(Color.ROT, "$name wird vom Arzt genäht. ")
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

enum class Fall { NOMINATIV, GENITIV, DATIV, AKKUSATIV }

enum class Geschlecht {
    MÄNNLICH {
        override fun unbestimmterArtikel(fall: Fall) = when (fall) {
            NOMINATIV -> "ein"
            AKKUSATIV -> "einen"
            DATIV -> "einem"
            GENITIV -> "eines"
        }
    },
    WEIBLICH {
        override fun unbestimmterArtikel(fall: Fall) = when (fall) {
            NOMINATIV, AKKUSATIV -> "eine"
            DATIV, GENITIV -> "einer"
        }
    },
    SACHLICH {
        override fun unbestimmterArtikel(fall: Fall) = when (fall) {
            NOMINATIV, AKKUSATIV -> "ein"
            DATIV -> "einem"
            GENITIV -> "eines"
        }
    };

    abstract fun unbestimmterArtikel(fall: Fall): String
}

interface MitBeschreibung {
    val name: String
    val beschreibung: String
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

class Kuscheltier : Geschenk(
        name = "Kuscheltier",
        geschlecht = Geschlecht.SACHLICH,
        beschreibung = "Kuscheltier mit Flöhen im Fell")

class Katze : Geschenk(
        name = "Katze",
        geschlecht = Geschlecht.WEIBLICH,
        beschreibung = "sehr bissige Katze")
