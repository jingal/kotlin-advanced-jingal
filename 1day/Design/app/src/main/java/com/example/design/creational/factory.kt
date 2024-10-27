package com.example.design.creational

class ConsoleLogger {
    fun log(message: String) { println("Console Log: $message") }
}

class FileLogger {
    fun log(message: String) { println("File Log: $message") }
}

class NetworkLogger {
    fun log(message: String) { println("Network Log: $message") }
}

fun main() {
    val loggerType = "console" // 조건에 따라 로거 유형 선택
    val logger = when (loggerType) {
        "console" -> ConsoleLogger()
        "file" -> FileLogger()
        "network" -> NetworkLogger()
        else -> throw IllegalArgumentException("Unknown logger type")
    }
    // logger.log("This is a log message.")
    ConsoleLogger().log("This is a log message.")
}
