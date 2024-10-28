package com.example.tdd

import io.mockk.every
import junit.framework.TestCase.assertEquals
import org.junit.Test
import io.mockk.mockk
import io.mockk.verify

class CalculatorUnitTest {
    @Test
    fun addNumbers() {
        // Given
        val calculator = Calculator()
        val num1 = 3
        val num2 = 5
        val expected = 8

        // When
        val result = calculator.add(num1, num2)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun mockkAdd() {
        val num1 = 3
        val num2 = 5
        val expected = 8
        val calculator = mockk<Calculator>()

        // Mock 동작 설정
        every { calculator.add(num1, num2) } returns expected

        // 함수 호출 및 결과 확인
        val result = calculator.add(num1, num2)
        assertEquals(expected, result)

        // 함수가 호출되었는지 검증
        verify { calculator.add(num1, num2) }
    }
}
