@file:Suppress("unused", "FunctionName", "SpellCheckingInspection", "EXPERIMENTAL_FEATURE_WARNING", "NonAsciiCharacters")

/**
 * Datei als Vorbereitung für Live Coding.
 */
package app.codedojo.kata.weihnachtsgeschichte

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

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
                && zielgeschwindigkeit.höherAls(Geschwindigkeit(135))
        ) {
            return Observable.error<Geschwindigkeit> {
                IllegalArgumentException("Zielgeschwindigkeit zu hoch.")
            }
        }

        return Observable
                .fromIterable(0..zielgeschwindigkeit.kmh)
                .filter { it % 50 == 0 || it == zielgeschwindigkeit.kmh }
                .map { Geschwindigkeit(it) }
    }
}

fun main() {
    Fahrzeug(Fahrer("Benno"))
            .beschleunigeAuf(Geschwindigkeit(173), Land("Grosskanton"))
            .subscribeBy(
                    onNext = { println(it) },
                    onError = {
                        println("Beschleunigung abgelehnt. $it")
                    }
            )

    Fahrzeug(Fahrer("Krunoslav"))
            .beschleunigeAuf(Geschwindigkeit(173), Land("Schweiz"))
            .subscribeBy(
                    onNext = { println(it) },
                    onError = {
                        println("Beschleunigung abgelehnt. $it")
                    }
            )

//    Observable.error<IllegalArgumentException> { IllegalArgumentException("So aber nicht! Tztztz!") }
//            .subscribeBy(
//                    onNext = { `drucke in Farbe`(Farbe.GELB, "Es wurde etwas emmitiert! 😎") },
//                    onError = {
//                        `drucke in Farbe`(Farbe.ROT, "Es ist ein Fehler aufgetreten 😱 \n\t $it")
//                    }
//            )
//
//    Observable
//            .fromIterable(1..100)
//            .doOnNext { if (it == 4) throw Error("Our chinise customer don't like this number!") }
//            .doOnError { println("Ohoh… Possibly the delivery of an unpopular number was blocked.") }
//            .subscribeBy(
//                    onNext = { println("Die aktuelle Zahl ist $it") },
//                    onError = { println("Fehler: $it") },
//                    onComplete = { println("Abgeschlossen.") }
//            )
//    printTR()
//
//    Observable
//            .fromIterable(1..100)
//            .doOnNext { if (it == 4) throw Error("Our chinise customer don't like this number!") }
//            .onErrorReturnItem(8)
//            .subscribeBy(
//                    onNext = { println("Die aktuelle Zahl ist $it") },
//                    onError = { println("Fehler: $it") },
//                    onComplete = { println("Abgeschlossen.") }
//            )
//    printTR()
//
//    Observable
//            .fromIterable(1..100)
//            .doOnNext { if (it == 4) throw Error("Our chinise customer don't like this number!") }
//            .onErrorResumeNext(Observable.empty())
//            .subscribeBy(
//                    onNext = { println("Die aktuelle Zahl ist $it") },
//                    onError = { println("Fehler: $it") },
//                    onComplete = { println("Abgeschlossen.") }
//            )
//    printTR()
}

fun printTR() {
    println("x".repeat(140).plus("\n"))
}
