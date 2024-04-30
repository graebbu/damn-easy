package org.example

import kotlinx.coroutines.*
import java.time.LocalTime

suspend fun p129() = runBlocking {
    println("${LocalTime.now()} : start")
    val a = coroutineScope {
        delay(1000)
        10
    }

    println("${LocalTime.now()} : a is calculated")
    val b = GlobalScope.launch {
        delay(1000)
        20
    }

    println("${LocalTime.now()} : $a")
    println("${LocalTime.now()} : $b")
}

suspend fun p131() = runBlocking(CoroutineExceptionHandler { coroutineContext, throwable ->
    println("$throwable")
}) {
    println("${LocalTime.now()} : start")
    val a = coroutineScope {
        delay(2000)
        coroutineScope {
            println("자식1")

        }
        coroutineScope {
            throw NullPointerException()
            println("자식2")
        }
        coroutineScope {
            println("자식3")

        }
        10
    }

    println("${LocalTime.now()} : a is calculated")
    val b = coroutineScope {
        delay(1000)
        throw NullPointerException()
        20
    }

    println("${LocalTime.now()} : $a")
    println("${LocalTime.now()} : $b")
}

data class Details(val name: String, val followers: Int)
class Tweet(val text: String)
class ApiException(
    val code: Int,
    message: String
) : Throwable(message)

fun getFollowersNumber(): Int =
    throw ApiException(500, "Service unavailable")

suspend fun getUserName(): String {
    delay(500)
    return "marcinmoskala"
}

suspend fun getTweets(): List<Tweet> {
    return listOf(Tweet("Hello, world"))
}

suspend fun getUserDetails(): Details = supervisorScope {
    val userName = async { getUserName() }
    val followersNumber = async(CoroutineExceptionHandler { coroutineContext, throwable ->
        println("umm.. $throwable")
    }) { getFollowersNumber() }

    val followersResponse = try {
        followersNumber.await()
    } catch (e: Exception) {
        println("$e")
        0
    }
    println(". $followersResponse")
    Details(userName.await(), followersResponse)
}

suspend fun p131_2() = runBlocking {
    val details = try {
        getUserDetails()
    } catch (e: ApiException) {
        println("$e")
        null
    }
    val tweets = async { getTweets() }
    val scope = CoroutineScope(Dispatchers.Main)
    println("User: $details")
    println("tweet result: ${scope}")
    TestUseCase(scope)
    testUseCase(scope)
    println("Tweets: ${tweets.await()}")
}

class TestUseCase(scope: CoroutineScope) {
    init {
        println("scope class: ${scope}")
    }
}

fun testUseCase(scope: CoroutineScope) {
    println("scope fun: ${scope}")
}

