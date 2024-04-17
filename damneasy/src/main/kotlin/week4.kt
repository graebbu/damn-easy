package org.example

import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

suspend fun week4_1() {
    coroutineScope {
        val job = launch {
            repeat(1000) { i ->
                delay(100)
                Thread.sleep(100)
                println("Printing $i : ${Thread.currentThread().name}")
            }
        }
        delay(1000)
        job.cancel()
        println("Cancelled successfully : ${Thread.currentThread().name}")
    }
}

suspend fun week4_2() {
    coroutineScope {
        val job = Job()
        var count = 0
        launch(job) {
            do {
                Thread.sleep(200)
                println("Printing ${count++}")
            } while (isActive)
        }
        delay(1100)
        job.cancelAndJoin()
        println("Cancelled successfully")
    }
}

suspend fun week4_3() {
    coroutineScope {
        val job = Job()
        launch(job) {
            repeat(1000) { i ->
                Thread.sleep(200)
                delay(1)
                println("Printing $i : ${job}")
            }
        }
        delay(1100)
        job.cancelAndJoin()
        println("Cancelled successfully")
        delay(1000)
    }
}

suspend fun week4_4() = coroutineScope{
    val job = Job()
    launch(job) {
        try {
            delay(2000)
            println("Job is done")
        } finally {
            println("Finally")
            launch { // 무시됩니다.
                println("Will not be printed")
            }
//            try {
                delay(1000) // 여기서 예외가 발생합니다.
                println("Will not be printed")
//            } catch (e: Exception) {
//                println("try : $e")
//            }
        }
    }
    delay(1000)
    job.cancelAndJoin()
    println("Cancel done")
}