@file:Suppress("MoveLambdaOutsideParentheses", "NonAsciiCharacters", "PackageDirectoryMismatch")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.rxkXX1

import io.reactivex.Observable


fun main() {
    Observable.just(Pair(0, 1))
            .repeat()
            .scan { p, _ -> Pair(p.second, Math.addExact(p.first, p.second)) }
            .map { it.second }
            .onErrorResumeNext(Observable.empty())
            .subscribe(::println)
}
