package com.example.effective

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.FileReader
import kotlin.properties.Delegates

suspend fun main() {
    main8()
}

// No.1
suspend fun main1() {
    var num = 0
    coroutineScope {
        for (i in 1..1000) {
            launch {
                delay(10)
                num += 1
            }
        }
    }
    print(num)
    // Atomic
}

// No.4
open class Animal
class Zebra : Animal()

fun main4() {
    var animal = Zebra()
    // animal = Animal() // Error: Type mismatch
}

// No.8
var number: Int by Delegates.notNull()

fun initializeNumber() {
    number = 42
}

fun printNumber() {
    println("Number is: $number")
}

fun main8() {
    initializeNumber()
    printNumber()
}

// No.9
fun countCharactersInFile(path: String): Int {
    val reader = BufferedReader(FileReader(path))
    try {
        return reader.lineSequence().sumOf { it.length }
    } finally {
        reader.close()
    }
}
