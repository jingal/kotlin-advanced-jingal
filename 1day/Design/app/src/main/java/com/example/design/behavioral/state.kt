package com.example.design.behavioral

class MusicPlayer {
    var state: String = "stopped"

    fun play() {
        if (state == "stopped" || state == "paused") {
            state = "playing"
            println("Music is now playing")
        } else {
            println("Already playing")
        }
    }

    fun pause() {
        if (state == "playing") {
            state = "paused"
            println("Music is now paused")
        } else {
            println("Cannot pause")
        }
    }

    fun stop() {
        state = "stopped"
        println("Music is now stopped")
    }
}

fun main() {
    val player = MusicPlayer()
    player.play()
    player.pause()
    player.stop()
}
