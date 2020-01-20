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
    // Einen Gruusigen instantiieren.
    // Ein Set mit einem Einwohner instanziieren.
    // Luustighuusen mit Einwohnern und Gruusigem instanziieren.
    val einwohnerSet = `einwohner aus Datei lesen`()
    val lustighuusen = Lustighuusen(einwohnerSet, Gruusige())
//    `einwohner ausgeben`(einwohnerSet)

    with(lustighuusen) {
        for (i in 1..25) {
            val geschenk = fabrik.`erstelle Geschenk zufälligen Typs`()
            gruusige.`liefere Geschenk an Einwohner`(geschenk, einwohnerSet.random())
        }
    }
}

// Erstelle eine Function «`einwohner aus Datei lesen`(): Set<Einwohner>».
// Nutze «File("src/main/resources/vornamen.txt").useLines» um die Datei einzulesen. «map» und «toSet» helfen beim Umformen.
fun `einwohner aus Datei lesen`(): Set<Einwohner> {

    return File("src/main/resources/vornamen.txt").useLines { vornamen ->
        vornamen.map { vorname -> Einwohner(vorname) }.toSet()
    }
}

// Erstelle eine Function «`einwohner ausgeben`(einwohner: Set<Einwohner>)».
//Nutze «einwohner.withIndex» in Verbindung mit «for» um die Namen aller Einwohner und dessen Index auszugeben.
//Tip: ⌥ + ⮐ hilft Dir dabei effizient zu programmieren!
fun `einwohner ausgeben`(einwohner: Set<Einwohner>) {
    for ((i, einwohner1) in einwohner.withIndex()) {
        `drucke in Farbe`(Farbe.GRUEN, "$i: ${einwohner1.name}")
    }
}

// Hier bitte den Code einfügen...

// Einwohner
//+ name: String {readOnly}
//+ guteLauneIndex: Int = 100
class Einwohner(val name: String) {
    var guteLauneIndex: Int = 100
        private set

    // Erstelle in der Klasse Einwohner eine neue Function «`nehme Geschenk an`(geschenk: Geschenk)»
    // Nutze die Function «drucke in Farbe» um einen Text in gelber Farbe in etwa wie folgt auszugeben…
    // Ronaldo nimmt Kuscheltier entgegen 🥰.
    fun `nehme Geschenk an`(geschenk: Geschenk) {
        `drucke in Farbe`(Farbe.GELB, "$name nimmt ${geschenk.name} entgegen 🥰")
        `spiele mit Geschenk`(geschenk)
    }

    // Erstelle in der Klasse Einwohner eine neue private Function «`spiele mit Geschenk`(geschenk: Geschenk)»
    //Nutze «when() {…is…» um die unterschiedlichen Fälle abzuhandeln und einen passenden Text in roter Farbe auszugeben, z.B…
    //Ronaldo kratzt sich die Haut auf 🤬.
    private fun `spiele mit Geschenk`(geschenk: Geschenk) =
            when (geschenk) {
                is T100 -> `drucke in Farbe`(Farbe.ROT, "$name wird vom T100 gepeinigt  🤬🤯")
                is BenutzteWindel -> `drucke in Farbe`(Farbe.ROT, "$name muss sich übergeben 🤮")
                is VerrueckterDueuetscher -> `drucke in Farbe`(Farbe.ROT, "$name wird sehr unfründlich genervt! Alle ussschaffe! 🚔")
                is SalzigerSchokkiKuss -> `drucke in Farbe`(Farbe.ROT, "$name zieht sich alles im Mund zusammen 🤢")
                is DefektesFahrrad -> `drucke in Farbe`(Farbe.ROT, "$name fährt gegen das Auto des Nachbarn. Das wird teuer! 🚲 🤕")
            }
}

class Gruusige {
    // Erweitere die Klasse «Gruusige» um eine neue Function «`liefere Geschenk an Einwohner`(geschenk: Geschenk, einwohner: Einwohner)».
    //Nutze die Function «`drucke in Farbe`» und die Enum «Geschlecht», um einen grammatikalisch korrekten Satz in der folgenden Form auszugeben…
    //Der Gruusige liefert ein Bonbon mit sehr scharfer Füllung an Johannes-Rudolf. 🥶
    //Rufe dann die Function «einwohner.`nehme Geschenk an`()» auf, um das Geschenk zu übergeben.
    fun `liefere Geschenk an Einwohner`(geschenk: Geschenk, einwohner: Einwohner) {
        `drucke in Farbe`(Farbe.GRUEN, "Der Gruusige liefert ${geschenk.geschlecht.unbestimmterArtikel(Fall.AKKUSATIV)} ${geschenk.beschreibung} an ${einwohner.name} aus 🥶.")
        einwohner.`nehme Geschenk an`(geschenk)
    }
}

// Lustighuusen
//+ einwohner: Set<Einwohner> = Set() {readOnly}
//+ gruusige: Gruusige; {readOnly}
//+ guteLauneIndex: Int = 100

// Verschiebe die «guteLauneIndex»-Eigenschaften aus den Konstruktoren in die Klassenkörper und schaue Dir an was dies verändert.
class Lustighuusen(val einwohner: Set<Einwohner>, val gruusige: Gruusige) {
    var guteLauneIndex: Int = 100
        private set

    // Erstelle eine neue Eigenschaft «fabrik> in der Klasse «Lustighuusen» und weise sogleich eine Fabrikinstanz zu.
    val fabrik: Fabrik = Fabrik()
}

// Erstelle eine Klasse «Fabrik»
//Erzeuge eine private Eigenschaft «geschenkeMaschine» vom Typ «Set<() -> Geschenk>»
//Weise direkt für jede Geschenkart einen Funktionsbody hinzu, der bei Aufruf (invoke) eine ebensolche erzeugt.
//setOf({..},{..) hilft Dir dabei.
class Fabrik {
    private val geschenkeMaschine: Set<() -> Geschenk> = setOf(
            { T100() },
            { BenutzteWindel() },
            { VerrueckterDueuetscher() },
            { SalzigerSchokkiKuss() },
            { DefektesFahrrad() })

    // Füge der Klasse eine function «`erstelle Geschenk zufälligen Typs`()» hinzu, die ein zufälliges Geschenk erzeugt und zurückgibt.
    fun `erstelle Geschenk zufälligen Typs`() = geschenkeMaschine.random().invoke()
}


// Geschenk
//+ name: String {readOnly}
//+ geschlecht: Geschlecht {readOnly}
//+ beschreibung: String {readOnly}
//+ stimmungspunkte: Int {readOnly}
sealed class Geschenk(val name: String, val geschlecht: Geschlecht, val beschreibung: String, val stimmungspunkte: Int)

class T100() : Geschenk("T100", Geschlecht.MÄNNLICH, "Roboter mit feindlicher Einstellung", 80)
class BenutzteWindel() : Geschenk("Benutzte Windel", Geschlecht.WEIBLICH, "Windel die sehr stark stinkt.", 30)
class VerrueckterDueuetscher() : Geschenk("Verrückter Düütscher", Geschlecht.MÄNNLICH, "verrückter Mensch mit germanischen Wurzeln.", 45)
class SalzigerSchokkiKuss() : Geschenk("Sehr salziger Schokkikuss", Geschlecht.MÄNNLICH, "Schokkikuss der ganz ekelig schmeckt.", 25)
class DefektesFahrrad() : Geschenk("Defektes Rad", Geschlecht.SACHLICH, "Fahrrad dessen Bremse nicht immer funktioniert.", 60)
