@file:Suppress("MoveLambdaOutsideParentheses", "NonAsciiCharacters", "PackageDirectoryMismatch", "FunctionName")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.rxk4

import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random.Default.nextInt

fun main() {
    Single.just("somePaylodData")
            .map { `liefere Wert oder Ausnahme`() }
            .retryWhen { it.take(5).delay(1, TimeUnit.SECONDS) }
            .subscribeBy(onSuccess = ::println)

    while (true) {
        Thread.sleep(1000)
    }
}

fun `liefere Wert oder Ausnahme`(): String {
    if (nextInt(5) < 2) {
        println("Erstelle UUID")
        return UUID.randomUUID().toString()
    } else {
        println("Werfe Fehler")
        throw ArithmeticException("Imaginäre Zahl nicht erlaubt.")
    }
}
