package org.example

import kotlinx.coroutines.*
import kotlin.time.measureTime

suspend fun week3_1() {
    coroutineScope {
        val job = Job()
        println("Text0 parent : ${this.coroutineContext.job.parent} / ${this.coroutineContext[CoroutineName]}")
        println("Text0 : ${this.coroutineContext.job} / ${this.coroutineContext[CoroutineName]}")
        println("Text0 children : ${this.coroutineContext.job.children} / ${this.coroutineContext[CoroutineName]}")
        launch(job + CoroutineName("test")) {
            println("Text1 parent : ${this.coroutineContext.job.parent} / ${this.coroutineContext[CoroutineName]}")
            println("Text1 : ${this.coroutineContext.job} / ${this.coroutineContext[CoroutineName]}")
            println("Text1 children : ${this.coroutineContext.job.children} / ${this.coroutineContext[CoroutineName]}")
            launch {
                delay(1000)
                println("Text2 parent : ${this.coroutineContext.job.parent} / ${this.coroutineContext[CoroutineName]}")
                println("Text2 : ${this.coroutineContext.job} / ${this.coroutineContext[CoroutineName]}")
                println("Text2 children : ${this.coroutineContext.job.children} / ${this.coroutineContext[CoroutineName]}")
            }
            launch {
                delay(1000)
                println("Text3 parent : ${this.coroutineContext.job.parent} / ${this.coroutineContext[CoroutineName]}")
                println("Text3 : ${this.coroutineContext.job} / ${this.coroutineContext[CoroutineName]}")
                println("Text3 children : ${this.coroutineContext.job.children} / ${this.coroutineContext[CoroutineName]}")
            }
        }
        launch(job) {
            delay(1000)
            println("Text4 parent : ${this.coroutineContext.job.parent} / ${this.coroutineContext[CoroutineName]}")
            println("Text4 : ${this.coroutineContext.job} / ${this.coroutineContext[CoroutineName]}")
            println("Text4 children : ${this.coroutineContext.job.children} / ${this.coroutineContext[CoroutineName]}")
        }
        launch {
            delay(1000)
            println("Text5 parent : ${this.coroutineContext.job.parent} / ${this.coroutineContext[CoroutineName]}")
            println("Text5 : ${this.coroutineContext.job} / ${this.coroutineContext[CoroutineName]}")
            println("Text5 children : ${this.coroutineContext.job.children} / ${this.coroutineContext[CoroutineName]}")
        }
    }
}

suspend fun week3_2() {
    coroutineScope {
        val job = Job()
        launch(job) {
            delay(1000)
            println("Text1")
        }
        launch(job) {
            delay(2000)
            println("Text2")
        }
        job.join() // 여기서 영원히 대기하게 됩니다.
        println("Will not be printed")
    }
}

suspend fun week3_3() {
    coroutineScope {
        val job = coroutineContext.job
        launch(job) {
            delay(1000)
            println("Text1")
        }
        launch(job) {
            delay(2000)
            println("Text2")
        }
        job.join()
        println("Will not be printed")
    }
}

suspend fun week3_4() = coroutineScope {
    val request = measureTime {
//        withContext(Dispatchers.Default) {
        launch {
            launch {
                println("job1: I run in my own Job and execute independently!")
                delay(1000)
                println("job1: I am not affected by cancellation of the request")
            }
            launch {
                delay(100)
                println("job2: I am a child of the request coroutine")
                delay(1000)
                println("job2: I will not execute this line if my parent request is cancelled")
            }
        }
//        }
    }
    println("$request")
//    delay(500)
//    request.cancel()
//    println("main: Who has survived request cancellation?")
//    delay(1000)
}

// "job1: I run in my own Job and execute independently!"
// "job2: I am a child of the request coroutine"
// "main: Who has survived request cancellation?"
// "job1: I am not affected by cancellation of the request"


suspend fun week3_5() = coroutineScope {
    val job = Job()
    launch(job) { // 새로운 잡이 부모로부터 상속받은 잡을 대체합니다.
        delay(1000)
        println("Text 1")
    }
    launch(job) { //새로운 잡이 부모로부터 상속받은 잡을 대체합니다.
        delay(2000)
        println("Text 2")
    }
    println("$job")
    job.complete()
    println("$job")
    job.join()
    println("$job")
}

suspend fun week3_6() = coroutineScope {
    val parentJob = Job()
    val job = Job(parentJob)
    launch(job) {
        delay (1000)
        println("Text 1")
    }
    launch(job) {
        delay(2000)
        println("Text 2")
    }
    delay(1100)
    parentJob.cancel()
    job.children.forEach { it.join() }
}