@file:Suppress("FunctionName", "unused", "SpellCheckingInspection", "NonAsciiCharacters", "PropertyName")

package app.codedojo.kata.weihnachtsgeschichte.loesungsvariante

import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Fall
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Farbe
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Geschlecht
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.`drucke in Farbe`
import io.reactivex.Observable
import java.io.File
import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    Lustighuusen(`einwohner aus Datei lesen`(), Gruusige()).run {
        Fabrik(5)
                .`produziere geschenke zufälligen Typs`(10)
                .subscribe { geschenk ->
                    println("Fabrik hat neues Geschenk ${geschenk.name} im Thread ${Thread.currentThread().name} fertiggestellt.")
                    gruusige.`liefere Geschenk an Einwohner`(geschenk, einwohner.random())
                }

        Thread.sleep(Long.MAX_VALUE)
    }
}

fun `einwohner aus Datei lesen`(): Set<Einwohner> {
    return File("src/main/resources/vornamen.txt").useLines { vornamen ->
        vornamen.map { vorname -> Einwohner(vorname) }.toSet()
    }
}

class Lustighuusen(val einwohner: Set<Einwohner>, val gruusige: Gruusige) {
    // Aufgabe 1: Ändern in computed getter der den Durchschnitt des GuteLauneIndex ausgibt.
    var guteLauneIndex: Int = 100
        private set

    val fabrik = Fabrik()

    val `zufälliger Einwohner`
        get() = einwohner.random()
}

class Einwohner(val name: String) {
    var guteLauneIndex: Int = 100
        private set

    fun `nehme Geschenk an`(geschenk: Geschenk) {
        `drucke in Farbe`(Farbe.GELB, "${this.name} nimmt ${geschenk.name} entgegen. 🥰")
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

class Fabrik(
        val anzahlMaschinen: Int = 3
) {
    private val geschenkeMaschine: Set<Produktionseinheit> = setOf(
            Produktionseinheit(Güterart("T100"), Duration.ofSeconds(8)) { T100() },
            Produktionseinheit(Güterart("Defektes Faherrad"), Duration.ofSeconds(6)) { DefektesFahrrad() },
            Produktionseinheit(Güterart("Verrückter Düütscher"), Duration.ofSeconds(4)) { VerrueckterDueuetscher() },
            Produktionseinheit(Güterart("Benutzte Windel"), Duration.ofSeconds(2)) { BenutzteWindel() },
            Produktionseinheit(Güterart("Salziger Schokkikuss"), Duration.ofSeconds(2)) { SalzigerSchokkiKuss() }
    )

    fun `produziere geschenke zufälligen Typs`(anzahlGeschenke: Int = 5): Observable<Geschenk> {
        return Observable
                .range(1, anzahlGeschenke)
                .flatMap(
                        {
                            `wähle Produktionseinheit zufälligen Typs`()
                                    .produziere()
                                    .doOnError { println("Es ist ein Fehler bei der Produktion aufgetreten: $it.") }
                                    .onErrorResumeNext(Observable.empty())
                        },
                        anzahlMaschinen
                )
    }

    fun `wähle Produktionseinheit zufälligen Typs`() = geschenkeMaschine.random()
}

class Produktionseinheit(
        val güterart: Güterart,
        val dauer: Duration,
        val prozess: () -> Geschenk
) {
    companion object {
        var seriennummer = AtomicInteger(0)
    }

    fun produziere(): Observable<Geschenk> {
        runCatching {
            val bezeichner = "${güterart.bezeichner} [${seriennummer.incrementAndGet()}]"

            return Observable
                    .timer(10, TimeUnit.MILLISECONDS)
                    .doOnSubscribe { println("Beginne Produktion von ${bezeichner}…") }
                    .map {
                        Thread.sleep(dauer.toMillis()) // Aufwändiger Produktionsprozess!
                        prozess()
                    }
                    .doAfterNext { println("Produktion von  ${bezeichner} abgeschlossen.") }
        }.getOrElse { return Observable.error(RuntimeException("Maschine explodiert.")) }
    }
}

inline class Güterart(val bezeichner: String)

sealed class Geschenk(val name: String, val geschlecht: Geschlecht, val beschreibung: String, val stimmungspunkte: Int)

class T100 : Geschenk("T100", Geschlecht.MÄNNLICH, "Roboter mit feindlicher Einstellung", 80)
class BenutzteWindel : Geschenk("benutzte Windel", Geschlecht.WEIBLICH, "Windel die sehr stark stinkt", 30)
class VerrueckterDueuetscher : Geschenk("verrückten Düütschen", Geschlecht.MÄNNLICH, "Menschen mit germanischen Wurzeln der echt verrückt ist", 45)
class SalzigerSchokkiKuss : Geschenk("sehr salzigen Schokkikuss", Geschlecht.MÄNNLICH, "Schokkikuss der ganz ekelig schmeckt", 25)
class DefektesFahrrad : Geschenk("defektes Rad", Geschlecht.SACHLICH, "Fahrrad dessen Bremse nicht immer funktioniert", 60)
