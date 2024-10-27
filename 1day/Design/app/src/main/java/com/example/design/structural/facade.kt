package com.example.design.structural

class SmtpServer {
    fun connect() { println("Connecting to SMTP server...") }
    fun authenticate() { println("Authenticating...") }
    fun sendEmail(subject: String, body: String) { println("Sending email with subject: $subject") }
    fun disconnect() { println("Disconnecting from SMTP server...") }
}

fun main() {
    val server = SmtpServer()
    server.connect()
    server.authenticate()
    server.sendEmail("Hello", "This is the email body.")
    server.disconnect()
}
