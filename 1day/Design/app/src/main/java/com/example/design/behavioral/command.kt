package com.example.design.behavioral

class CommandProcessor {
    fun execute(command: String) {
        when (command) {
            "save" -> println("Executing Save Command")
            "delete" -> println("Executing Delete Command")
            "send" -> println("Executing Send Command")
            else -> throw IllegalArgumentException("Unknown command")
        }
    }
}

fun main() {
    val processor = CommandProcessor()
    processor.execute("save")
    processor.execute("delete")
}
