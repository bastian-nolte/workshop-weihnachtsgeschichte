@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "PackageDirectoryMismatch", "unused")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.c3

fun main() {
    val audoA1 = Pkw(
            Marke("Audo"),
            Motor(Leistung(185), Hubraum(2400)))
    println("Ausgabe von println mit «toString»")
    println(audoA1)
}

data class Pkw(
        val marke: Marke,
        val motor: Motor)

data class Motor(
        val leistung: Leistung,
        var hubraum: Hubraum)

inline class Marke(val markenname: String)
inline class Leistung(val kilowatt: Int)
inline class Hubraum(val milliliter: Int)
