@file:Suppress("MoveLambdaOutsideParentheses", "NonAsciiCharacters")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.rxkXX3

import io.reactivex.Observable
import io.reactivex.ObservableEmitter


fun main() {
    zahlenreihe {
        startwert = Pair(0, 1)
        rechner = { (first, second) -> second to Result.runCatching { Math.addExact(first, second) }.getOrElse { -1 }}
        run().subscribe(::println)
    }


}

// Type-Safe Builder
fun zahlenreihe(init: Zahlenreihe.() -> Unit): Zahlenreihe {
    val zahlenreihe = Zahlenreihe()
    zahlenreihe.init()
    return zahlenreihe
}

class Zahlenreihe {
    var startwert: Pair<Int, Int> = Pair(0, 0)
    var rechner: ((Pair<Int, Int>) -> Pair<Int, Int>) = { Pair(0, 0) }

    fun run() = Observable.create { emitter: ObservableEmitter<Int> ->
        var p = startwert
        while (!emitter.isDisposed && p.second != -1) {
            emitter.onNext(p.second)
            p = rechner(p)
        }
    }
}
