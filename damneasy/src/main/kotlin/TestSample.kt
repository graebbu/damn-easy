package org.example

import kotlinx.coroutines.*
import java.time.LocalTime
import kotlin.coroutines.coroutineContext
import kotlin.time.measureTime

suspend fun test1() = runBlocking {
    val res1 = GlobalScope.async {
        delay(1000L)
        "Text 1"
    }
    val res2 = GlobalScope.async {
        delay(2000L)
        "Text 2"
    }
    val res3 = GlobalScope.async {
        delay(3000L)
        "Text 3"
    }


    println("start : ${LocalTime.now()}")
    println(res1.await() + ": ${LocalTime.now()}")
    println(res2.await() + ": ${LocalTime.now()}")
    println(res3.await() + ": ${LocalTime.now()}")
    println("end   : ${LocalTime.now()}")
}

suspend fun test2() = runBlocking {
    val res1 = GlobalScope.launch {
        delay(1000L)
        "Text 1"
    }
    val res2 = GlobalScope.launch {
        delay(2000L)
        "Text 2"
    }
    val res3 = GlobalScope.launch {
        delay(3000L)
        "Text 3"
    }

    println("Hello!")
}

suspend fun test3() = coroutineScope {
    val tag = "test3"
    println(tag)
    GlobalScope.launch {
        delay(1000L)
        println("Text 1")
    }
    GlobalScope.launch {
        delay(2000L)
        println("Text 2")
    }
    GlobalScope.launch {
        delay(3000L)
        println("Text 3")
    }
    println("Hello")
}

suspend fun test4() {
    val time = measureTime {
        coroutineScope {
            launch {
                delay(2000L)
                println("coroutine 1")
            }
            launch {
                delay(1000L)
                println("coroutine 2")
            }
        }
    }
    println(time)
}

suspend fun test5() = coroutineScope {
    println("test5")
    val res1 = GlobalScope.async {
        delay(1000L)
        "Text 1"
    }
    val res2 = GlobalScope.async {
        delay(2000L)
        "Text 2"
    }
    val res3 = GlobalScope.async {
        delay(3000L)
        "Text 3"
    }
    println(res1.await())
    println(res2.await())
    println(res3.await())
}

suspend fun test6() {
    CoroutineScope(Dispatchers.Default).launch {
        delay(1000L)
        println("빌더 : ${LocalTime.now()}")
        println("Text1 : ${coroutineContext.job} / ${coroutineContext[CoroutineName]}")
    }

    CoroutineScope(Dispatchers.Default).launch {
        delay(2000L)
        println("Text2 : ${coroutineContext.job} / ${coroutineContext[CoroutineName]}")
    }

    CoroutineScope(Dispatchers.Default).launch {
        delay(3000L)
        println("Text3 : ${coroutineContext.job} / ${coroutineContext[CoroutineName]}")
    }

    println("노상관 : ${LocalTime.now()}")
}

suspend fun test7() {
    coroutineContext.job.parent?.join()
    coroutineContext.job.children
}

suspend fun doWorld() = coroutineScope { // this: CoroutineScope
    launch {
        delay(2000L)
        println("World 2")
    }
    launch {
        delay(1000L)
        println("World 1")
    }
    println("Hello")
}

