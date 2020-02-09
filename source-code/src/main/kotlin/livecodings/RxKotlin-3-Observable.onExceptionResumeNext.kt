@file:Suppress("unused", "FunctionName", "SpellCheckingInspection", "EXPERIMENTAL_FEATURE_WARNING", "NonAsciiCharacters", "PackageDirectoryMismatch", "DuplicatedCode")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.rxk3

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import java.util.*


fun main() {
    Farm().schürfe()
            .subscribeBy(
                    onNext = { println("Wir werden reich! Neuer Block $it") },
                    onError = { println("Mist schon wieder alle Rechner kaputt!") },
                    onComplete = { println("Alle Berechnungen sind beendet.") }
            )
}

class Farm() {
    fun schürfe() = Miner(Bezeichnung("Computer der Tochter"))
            .schürfe()
            .onExceptionResumeNext(
                    Miner(Bezeichnung("Smartphone vom Kollegen"))
                            .schürfe())
}

class Miner(var bezeichnung: Bezeichnung) {
    fun schürfe() = Observable
            .fromIterable(0..Int.MAX_VALUE)
            .map { sehrKomplexeBerechnung(bezeichnung) }

    private fun sehrKomplexeBerechnung(bezeichnung: Bezeichnung): Block {
        if (0.7f < Math.random()) throw RuntimeException("CPU ist überhitzt.")
        return Block("${bezeichnung.id} >> LTC: ${UUID.randomUUID()}")
    }
}

inline class Bezeichnung(val id: String)

inline class Block(val inhalt: String)
