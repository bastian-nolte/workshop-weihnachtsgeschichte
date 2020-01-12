@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "FunctionName", "NonAsciiCharacters")

package app.codedojo.kata.weihnachtsgeschichte.livecodings

import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.Farbe
import app.codedojo.kata.weihnachtsgeschichte.vorbereitet.`drucke in Farbe`
import com.google.gson.Gson

fun main() {
    // Deep-Copy-Test
    `wie verhält sich copy in kotlin`()
}

fun `wie verhält sich copy in kotlin`() {
    val motor = Motor(Leistung(185), Hubraum(2500))
    val pkw = Pkw(Marke("Audos"), motor)
    val pkwKopie = pkw.copy(marke = Marke("tolosa"))
    val pkwDeepCopyGson = pkw.`deep copy with Gson`()

    println("Hubraum Originalmotor: ${motor.hubraum}")
    println("Hubraum Motor der PKW-Kopie: ${pkwKopie.motor.hubraum}")
    println("Neuer Hubraum Motor der PKW-Kopie mit Gson: ${pkwDeepCopyGson.motor.hubraum}")
    println()

    `drucke in Farbe`(Farbe.GELB, "Beweis: Kein Deep Copy durch die copy-Methode von Koltin:")
    motor.hubraum = Hubraum(2200)
    println("Neuer Hubraum Originalmotor: ${motor.hubraum}")
    println("Neuer Hubraum Motor der PKW-Kopie: ${pkwKopie.motor.hubraum}")
    println()

    `drucke in Farbe`(Farbe.GRUEN, "Beweis: Deep Copy durch externe Libraries funktioniert:")
    println("Neuer Hubraum Originalmotor: ${motor.hubraum}")
    println("Neuer Hubraum Motor der PKW-Kopie: ${pkwDeepCopyGson.motor.hubraum}")
}

data class Pkw(
        val marke: Marke,
        val motor: Motor) {

    // JSON Serialization with Gson (deep copy)
    fun `deep copy with Gson`(): Pkw {
        return Gson().fromJson(Gson().toJson(this), this.javaClass)
    }

    // Cloneable Interface und manuelle Implementierung für maximale Performance.
    // Hinweis: Hier nur erwähnt aber nicht implementiert.
}

data class Motor(
        val leistung: Leistung,
        // Read/Write damit wir Deep-Copy testen können.
        // Ist das ein gutes Pattern? Meist nicht wirklich.
        var hubraum: Hubraum)


inline class Marke(val markenname: String)
inline class Leistung(val kilowatt: Int)
inline class Hubraum(val liter: Int)
