package com.example.di

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class EnglishGreeting

@Qualifier
annotation class KoreanGreeting

@HiltAndroidApp
class HiltExampleApp : Application()

@AndroidEntryPoint
class HiltActivity : ComponentActivity() {

    @Inject
    lateinit var greetingServiceFactory: GreetingServiceFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltExampleApp(greetingServiceFactory)
        }
    }
}

@Composable
fun HiltExampleApp(greetingServiceFactory: GreetingServiceFactory) {
    var greeting by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val greetingService = greetingServiceFactory.createGreetingService(name)
            greeting = greetingService.greet(name)
        }) {
            Text("Greet")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = greeting, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

class GreetingServiceFactory @Inject constructor(
    private val englishGreetingService: GreetingService,
    private val koreanGreetingService: GreetingService
) {
    fun createGreetingService(input: String): GreetingService {
        return if (isKorean(input)) koreanGreetingService else englishGreetingService
    }

    private fun isKorean(input: String): Boolean {
        return input.any { it.code in 0xAC00..0xD7A3 }
    }
}

// 인터페이스와 구현체
interface GreetingService {
    fun greet(name: String): String
}

class EnglishGreetingService : GreetingService {
    override fun greet(name: String): String {
        return "Hello, $name!"
    }
}

class KoreanGreetingService : GreetingService {
    override fun greet(name: String): String {
        return "안녕하세요, $name!"
    }
}

// Hilt 모듈
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @EnglishGreeting
    fun provideEnglishGreetingService(): GreetingService {
        return EnglishGreetingService()
    }

    @Provides
    @Singleton
    @KoreanGreeting
    fun provideKoreanGreetingService(): GreetingService {
        return KoreanGreetingService()
    }

    @Provides
    @Singleton
    fun provideGreetingServiceFactory(
        @EnglishGreeting englishGreetingService: GreetingService,
        @KoreanGreeting koreanGreetingService: GreetingService
    ): GreetingServiceFactory {
        return GreetingServiceFactory(englishGreetingService, koreanGreetingService)
    }
}

