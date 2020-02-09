@file:Suppress("MoveLambdaOutsideParentheses", "NonAsciiCharacters", "PackageDirectoryMismatch")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.rxkXX1

import io.reactivex.Observable
import io.reactivex.ObservableEmitter


fun main() {
    Observable.create { emitter: ObservableEmitter<Int> ->
        var p = Pair(0, 1)
        while (!emitter.isDisposed) {
            emitter.onNext(p.second)
            runCatching { p = Pair(p.second, Math.addExact(p.first, p.second)) }.onFailure { emitter.onComplete() }
        }
    }.subscribe(::println)
}
