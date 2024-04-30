package org.example

import kotlinx.coroutines.*

suspend fun p115(): Unit = supervisorScope {
    // try-catch 구문으로 래핑하지 마세요. 무시됩니다.
    val test = async {
        delay(1000)
        throw Error("Some error")
    }
    try {
        test.await()
    } catch (e: Throwable) { // 여기선 아무 도움이 되지 않습니다.
        println("Will not be printed")
    }
    launch {
        delay(2000)
        println("Will not be printed")
    }
}

suspend fun p117() = coroutineScope {
    // 이렇게 하지 마세요. 자식 코루틴 하나가 있고
    // 부모 코루틴이 없는 잡은 일반 잡과 동일하게 작동합니다.
    launch(Job() + CoroutineName("parent")) { // 1

        println("parent : ${coroutineContext.job.parent}")
        println("current : ${coroutineContext.job}")
        println("children : ${coroutineContext.job.children}")

        launch {
            delay(1000)
            throw Error("Some Error")
//            println("Some Error")
            println("parent : ${coroutineContext.job.parent}")
            println("current : ${coroutineContext.job}")
            println("children : ${coroutineContext.job.children}")
        }
        launch {
            delay(2000)
            println("Will not be printed")
            println("parent : ${coroutineContext.job.parent}")
            println("current : ${coroutineContext.job}")
            println("children : ${coroutineContext.job.children}")
        }
    }
    delay(3000)
}

suspend fun p117_2() = coroutineScope {
    // 이렇게 하지 마세요. 자식 코루틴 하나가 있고
    // 부모 코루틴이 없는 잡은 일반 잡과 동일하게 작동합니다.
    val job = SupervisorJob() // 부모
    launch(job) { // 1
        delay(3000)
        println("자식1(${System.currentTimeMillis()}) : [${coroutineContext.job.parent}]  / [${coroutineContext.job}] / [${coroutineContext.job.children}]")
        launch {
            delay(1000)
            throw Error("Some Error")
            println("손주1(${System.currentTimeMillis()}) : [${coroutineContext.job.parent}]  / [${coroutineContext.job}] / [${coroutineContext.job.children}]")
        }
        launch {
            delay(2000)
            println("손주2(${System.currentTimeMillis()}) : [${coroutineContext.job.parent}]  / [${coroutineContext.job}] / [${coroutineContext.job.children}]")
        }
    }
    launch(job) {
        delay(2000)
        println("자식2(${System.currentTimeMillis()}) : [${coroutineContext.job.parent}]  / [${coroutineContext.job}] / [${coroutineContext.job.children}]")
    }
    delay(3000)
}

suspend fun p118(): Unit = runBlocking {
    supervisorScope {
        launch {
            delay(1000)
            throw Error("Some error")
        }
        launch {
            delay(2000)
            println("Will be printed")
        }
    }
//    delay(1000)
    println("Done")
}
// Exception...

object MyNonPropagatingException : CancellationException()

suspend fun p121(): Unit = coroutineScope {
    launch { // 1
        launch { // 2
            delay(2000)
            println("Will not be printed ")
        }
        throw CancellationException() // 3
    }
    launch { // 4
        delay(2000)
        println("Will be printed")
    }
}
//《2초 후》
// Will be printed