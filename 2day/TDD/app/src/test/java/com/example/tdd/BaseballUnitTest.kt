package com.example.tdd

import junit.framework.TestCase.assertEquals
import org.junit.Test

class BaseballUnitTest {
    @Test
    fun generateNumber() {
        // Given
        val generator = NumberGenerator()
        val expected = 3

        // When
        val result = generator.generate().size

        // Then
        assertEquals(expected, result)
    }
}
