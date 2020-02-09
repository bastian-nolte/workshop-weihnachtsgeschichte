@file:Suppress("unused", "FunctionName", "SpellCheckingInspection", "EXPERIMENTAL_FEATURE_WARNING", "NonAsciiCharacters", "PackageDirectoryMismatch", "DuplicatedCode")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.rxk1

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

fun main() {
    Fahrzeug(Fahrer("Benno"))
            .beschleunigeAuf(Geschwindigkeit(173), Land("Grosskanton"))
            .subscribeBy(
                    onNext = { println(it) },
                    onError = {
                        println("Beschleunigung abgelehnt. $it")
                    }
            )

    printTR()

    Fahrzeug(Fahrer("Krunoslav"))
            .beschleunigeAuf(Geschwindigkeit(173), Land("Schweiz"))
            .subscribeBy(
                    onNext = { println(it) },
                    onError = {
                        println("Beschleunigung abgelehnt. $it")
                    }
            )

    printTR()
    Fahrzeug(Fahrer("Oma Annelore"))
            .beschleunigeAuf(Geschwindigkeit(85), Land("Schweiz"))
            .subscribeBy(
                    onNext = {
                        println(it)
                        if (it.höherAls(Geschwindigkeit(45))) {
                            throw IllegalStateException("Oma mag diese hohe Geschwindigkeit nicht!")
                        }
                    },
                    onError = {
                        println("Beschleunigung abgelehnt. $it")
                    }
            )
}

inline class Geschwindigkeit(val kmh: Int) {
    fun höherAls(vergleichsgeschwindigkeit: Geschwindigkeit) = this.kmh > vergleichsgeschwindigkeit.kmh
}

inline class Land(val name: String)

data class Fahrer(val name: String)

class Fahrzeug(val fahrer: Fahrer) {
    fun beschleunigeAuf(
            zielgeschwindigkeit: Geschwindigkeit,
            aufenthalt: Land): Observable<Geschwindigkeit> {

        if (aufenthalt == Land("Schweiz")
                && zielgeschwindigkeit.höherAls(Geschwindigkeit(135))) {
            return Observable.error<Geschwindigkeit> { IllegalArgumentException("Zielgeschwindigkeit zu hoch.") }
        }

        return Observable
                .fromIterable(0..zielgeschwindigkeit.kmh)
                .filter { it % 50 == 0 || it == zielgeschwindigkeit.kmh }
                .map { Geschwindigkeit(it) }
    }
}

fun printTR() {
    println("x".repeat(140).plus("\n"))
}
