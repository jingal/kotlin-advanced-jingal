package com.example.variance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.variance.ui.theme.VarianceTheme

val intNumber : Int = 1
val number : Number = intNumber

class Box<out T>(val value: T) {
//    fun foo(animal: T) : T {
//
//    }
}

open class Animal {
    fun eat() {
        println("Animal is eating")
    }
}

class Dog : Animal() {
    fun bark() {
        println("Dog is barking")
    }
}

class Cat : Animal() {
    fun meow() {
        println("Cat is meowing")
    }
}

interface Consumer<in T> {
    fun consume(item: T)
}

class AnimalConsumer : Consumer<Animal> {
    override fun consume(item: Animal) {
        item.eat()
    }
}

fun feedDog(dogConsumer: Consumer<Dog>) {
    val dog = Dog()
    dogConsumer.consume(dog)
}

fun foo() {
    val l = listOf(1,2)
    val animalConsumer: Consumer<Animal> = AnimalConsumer()
    feedDog(animalConsumer)
}


fun printAnimalBox(animalBox: Box<Animal>) {
    println("This box contains an animal")
}

val dogBox: Box<Dog> = Box(Dog())
val catBox: Box<Cat> = Box(Cat())


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VarianceTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VarianceTheme {
        Greeting("Android")
    }
}