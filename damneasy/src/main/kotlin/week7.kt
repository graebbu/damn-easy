package org.example

import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.random.Random
import kotlin.time.measureTime

suspend fun p146() = coroutineScope {
    val dispatchers = Dispatchers.Default.limitedParallelism(5)
    repeat(1000) {
        launch(dispatchers) {
            List(1000) { Random.nextLong() }.maxOrNull()

            val threadName = Thread.currentThread().name
            println("Rinning on thread $threadName")
        }
    }
}

suspend fun p149() = measureTime {
    coroutineScope {
        repeat(50) {
            launch(Dispatchers.IO) {
                Thread.sleep(1000)
            }
        }
    }
}.let(::println)

suspend fun p149_io() = coroutineScope {
    repeat(1000) {
        launch(Dispatchers.IO) {
            List(1000) { Random.nextLong() }.maxOrNull()

            val threadName = Thread.currentThread().name
            println("Rinning on thread $threadName")
        }
    }
}

suspend fun p154() = coroutineScope {
    var i = 0
    repeat(10000) {
        launch(Dispatchers.IO) {
            i++
        }
    }
    delay(1000)
    println(i)
}

suspend fun p158() = withContext(newSingleThreadContext("Thread1")) {
    var continuation: Continuation<Unit>? = null

    launch(newSingleThreadContext("Thread2")) {
        delay(1000)
        println("resume")
        continuation?.resume(Unit)
    }

    launch(Dispatchers.Unconfined) {
        println("first : ${Thread.currentThread().name}")

        suspendCancellableCoroutine {
            continuation = it
            println("suspendCancellableCoroutine")
        }

        println("ing.. : ${Thread.currentThread().name}")

        delay(1000)

        println("last : ${Thread.currentThread().name}")
    }

}