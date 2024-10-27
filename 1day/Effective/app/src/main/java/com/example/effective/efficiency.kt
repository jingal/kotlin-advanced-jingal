package com.example.effective

import kotlin.system.measureTimeMillis

fun main() {
    main50()
}

// No.46
inline fun performTask(action: () -> Unit) {
    println("Task 시작")
    action()
    println("Task 종료")
}

fun main46() {
    performTask {
        println("작업 실행 중")
        return@performTask  // 비지역 리턴 가능
    }

    repeat(10) {
        print(it)
    }
}

// No.50
data class Student(val name: String?, val grade: Int)

fun List<Student>.getNamesWorks(): List<String> = this
    .map { it.name }
    .filter { it != null }
    .map { it!! }

// Better
fun List<Student>.getNamesBetter(): List<String> = this
    .map { it.name }
    .filterNotNull()

// Best
fun List<Student>.getNamesBest(): List<String> = this
    .mapNotNull { it.name }

fun main50() {
    val list = List(1_000_000) { Student(if (it % 2 == 0) "Student$it" else null, it) }

    val timeWorks = measureTimeMillis {
        val result = list.getNamesWorks()
        println("getNamesWorks 결과 개수: ${result.size}")
    }

    val timeBetter = measureTimeMillis {
        val result = list.getNamesBetter()
        println("getNamesBetter 결과 개수: ${result.size}")
    }

    val timeBest = measureTimeMillis {
        val result = list.getNamesBest()
        println("getNamesBest 결과 개수: ${result.size}")
    }

    // 결과 출력
    println("getNamesWorks 실행 시간: $timeWorks ms")
    println("getNamesBetter 실행 시간: $timeBetter ms")
    println("getNamesBest 실행 시간: $timeBest ms")
}

