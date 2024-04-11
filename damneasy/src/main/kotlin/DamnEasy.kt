package org.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun damnEasy() {
    var count = 0
    while (true) {
        delay(1000)
        count++
        println(count)
    }

}