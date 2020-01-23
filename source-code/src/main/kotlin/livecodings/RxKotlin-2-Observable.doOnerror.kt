@file:Suppress("unused", "FunctionName", "SpellCheckingInspection", "EXPERIMENTAL_FEATURE_WARNING", "NonAsciiCharacters", "PackageDirectoryMismatch", "DuplicatedCode")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.rxk2

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

fun main() {
    Observable
            .fromIterable(0..300 step 50)
            .doOnNext { if (it > 120) throw RuntimeException("Ausnahme bei Wert $it") }
            .doOnError { println("$it war wohl zu schnell 😅") }
            .subscribeBy(
                    onNext = { println("Aktueller Wert ist $it") },
                    onError = { println("Fehler aufgtraten: $it") }
            )
}
