package com.example.design.structural

class Notification {
    fun send(message: String) {
        println("Sending basic notification: $message")
    }
}

class NotificationManager {
    fun sendEmailNotification(message: String) {
        println("Sending email notification: $message")
    }

    fun sendSmsNotification(message: String) {
        println("Sending SMS notification: $message")
    }

    fun sendPushNotification(message: String) {
        println("Sending push notification: $message")
    }
}

fun main() {
    val manager = NotificationManager()
    val message = "Hello, this is an important notification."
    manager.sendEmailNotification(message)
    manager.sendSmsNotification(message)
}
