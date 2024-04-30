package org.example

import kotlin.system.measureTimeMillis


//TIP Press <shortcut raw="SHIFT"/> twice to open the Search Everywhere dialog and type <b>show whitespaces</b>,
// then press <shortcut raw="ENTER"/>. You can now see whitespace characters in your code.
suspend fun main() {
//    p142()
    val time = measureTimeMillis {
        p142Test()
    }
    println("main : $time ms")
}
