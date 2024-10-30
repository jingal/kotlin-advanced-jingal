package com.example.fp

import com.example.fp.Optional.None
import com.example.fp.Optional.Some

fun main() {
    oopMain()
    fpMain()
    monadMain()
}
// OOP
class Circle(private val radius: Double) {
    fun area(): Double {
        return Math.PI * radius * radius
    }

    fun circumference(): Double {
        return 2 * Math.PI * radius
    }
}

fun oopMain() {
    val circle = Circle(5.0)
    println("OOP Area: ${circle.area()}")
    println("OOP Circumference: ${circle.circumference()}")
}

// FP
fun circleArea(radius: Double): Double {
    return Math.PI * radius * radius
}

fun circleCircumference(radius: Double): Double {
    return 2 * Math.PI * radius
}

// diameter

fun fpMain() {
    val radius = 5.0
    println("FP Area: ${circleArea(radius)}")
    println("FP Circumference: ${circleCircumference(radius)}")
}

// FP (Option 모나드)
@Suppress("UNCHECKED_CAST")
sealed class Optional<out T> {
    data object None : Optional<Nothing>()
    data class Some<T>(val value: T) : Optional<T>()

    fun <R> map(f: (T) -> R): Optional<R> {
        return when (this) {
            is None -> None as Optional<R>
            is Some -> Some(f(value))
        }
    }

    fun <R> flatMap(f: (T) -> Optional<R>): Optional<R> {
        return when (this) {
            is None -> None as Optional<R>
            is Some -> f(value)
        }
    }
}

fun circleAreaMonad(radius: Double): Optional<Double> {
    return if (radius >= 0) {
        Some(Math.PI * radius * radius)
    } else {
        None
    }
}

fun circleCircumferenceMonad(radius: Double): Optional<Double> {
    return if (radius >= 0) {
        Some(2 * Math.PI * radius)
    } else {
        None
    }
}

fun monadMain() {
    val radius = 5.0
    circleAreaMonad(radius).flatMap { area ->
        circleCircumferenceMonad(radius).map { circumference ->
            println("Monad Area: $area")
            println("Monad Circumference: $circumference")
        }
    }
}
