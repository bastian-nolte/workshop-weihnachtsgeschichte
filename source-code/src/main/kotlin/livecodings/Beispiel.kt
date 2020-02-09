@file:Suppress("MoveLambdaOutsideParentheses", "NonAsciiCharacters")

package app.codedojo.kata.weihnachtsgeschichte.livecodings.bsp1

import io.reactivex.BackpressureOverflowStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


fun main() {
    val interval = Flowable.range(0, 100)
    interval
            .parallel(10)
            .runOn(Schedulers.computation())
            .map { performLongOperation() }
            .sequential()
            .subscribe { println("X: $it") }

    Observable.interval(100, TimeUnit.MILLISECONDS)
//    Observable.range(1, 10)
            .flatMap {
                Observable.just("")
                        .subscribeOn(Schedulers.computation())
                        .map<Any> { performLongOperation() }
            }.subscribe { println("X: $it") }

    while (true) {
        Thread.sleep(400)
    }
}

fun performLongOperation(): String {
    val uuid = UUID.randomUUID().toString()
    println("Start: ${Thread.currentThread().name} $uuid")
    Thread.sleep((Random.nextLong(10) * 1000))
    return uuid
}
