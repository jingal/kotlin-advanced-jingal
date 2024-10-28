package com.example.di

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class GreetingServiceFactoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var greetingServiceFactory: GreetingServiceFactory

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testEnglishGreetingService() {
        // When
        val greetingService = greetingServiceFactory.createGreetingService("John")
        val greeting = greetingService.greet("John")

        // Then
        assertEquals("Hello, John!", greeting)
    }

    @Test
    fun testKoreanGreetingService() {
        // When
        val greetingService = greetingServiceFactory.createGreetingService("홍길동")
        val greeting = greetingService.greet("홍길동")

        // Then
        assertEquals("안녕하세요, 홍길동!", greeting)
    }
}
