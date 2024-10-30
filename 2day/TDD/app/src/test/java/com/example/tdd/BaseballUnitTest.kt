package com.example.tdd

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Test
import org.junit.Before
import org.junit.Ignore

class NumberGeneratorTest {

    @Ignore("제외하기")
    @Test
    fun generateNumber() {
        // Given
        val generator = NumberGenerator()
        val expected = 3

        // When
        val result = generator.generate().size

        // Then
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun generateReturns3UniqueDigitsBetween0And9() {
        // Given: NumberGenerator 초기화
        val generator = NumberGenerator()

        // When: 3자리 고유 숫자 생성
        val numbers = generator.generate()

        // Then: 정확히 3개의 고유한 숫자가 0에서 9 사이의 값인지 확인
        assertEquals(3, numbers.size)
        assertEquals(numbers.size, numbers.toSet().size)
        assertTrue(numbers.all { it in 0..9 })
    }
}

class InputValidatorTest {

    private val validator = InputValidator()

    @Test
    fun validateReturnsListOfIntegersForValidInput() {
        // Given: 유효한 3자리 숫자 문자열
        val input = "123"
        val expected = listOf(1, 2, 3)

        // When: validate 호출
        val result = validator.validate(input)

        // Then: 올바른 리스트 반환
        assertEquals(expected, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun validateThrowsExceptionIfInputIsNot3Digits() {
        // Given: 길이가 3이 아닌 입력값
        val input = "12"

        // When: validate 호출 시
        validator.validate(input)

        // Then: IllegalArgumentException 예외 발생
    }

    @Test(expected = IllegalArgumentException::class)
    fun validateThrowsExceptionIfInputContainsNonDigitCharacters() {
        // Given: 숫자가 아닌 문자가 포함된 입력값
        val input = "12a"

        // When: validate 호출 시
        validator.validate(input)

        // Then: IllegalArgumentException 예외 발생
    }

    @Test(expected = IllegalArgumentException::class)
    fun validateThrowsExceptionIfInputContainsDuplicateDigits() {
        // Given: 중복된 숫자가 포함된 입력값
        val input = "112"

        // When: validate 호출 시
        validator.validate(input)

        // Then: IllegalArgumentException 예외 발생
    }
}

class BallCounterTest {

    private val counter = BallCounter()

    @Test
    fun countReturnsCorrectStrikesAndBalls() {
        // Given: 정답 숫자 리스트
        val answer = listOf(1, 2, 3)

        // When: 각 테스트 케이스에 대해 count 호출

        // Then: 예상 결과와 일치하는지 확인

        // Test case 1: 3 strikes
        val guess1 = listOf(1, 2, 3)
        assertEquals(BallCount(strikes = 3, balls = 0), counter.count(answer, guess1))

        // Test case 2: 1 strike, 2 balls
        val guess2 = listOf(1, 3, 2)
        assertEquals(BallCount(strikes = 1, balls = 2), counter.count(answer, guess2))

        // Test case 3: 0 strikes, 3 balls
        val guess3 = listOf(3, 1, 2)
        assertEquals(BallCount(strikes = 0, balls = 3), counter.count(answer, guess3))

        // Test case 4: no strikes, no balls
        val guess4 = listOf(4, 5, 6)
        assertEquals(BallCount(strikes = 0, balls = 0), counter.count(answer, guess4))
    }
}

class BaseballTest {

    private lateinit var baseball: Baseball

    @Before
    fun setup() {
        val mockGenerator = mockk<NumberGenerator>()
        every { mockGenerator.generate() } returns listOf(1, 2, 3)

        baseball = Baseball(mockGenerator)
    }

    @Test
    fun playReturnsCorrectBallCountForCorrectGuess() {
        // Given: 정답이 1, 2, 3인 상태에서 올바른 추측값
        val guess = "123"

        // When: play 메서드 호출
        val result = baseball.play(guess)

        // Then: 3 strikes, 0 balls 결과 확인
        assertEquals(BallCount(strikes = 3, balls = 0), result)
    }

    @Test
    fun playReturnsCorrectBallCountForPartiallyCorrectGuess() {
        // Given: 정답이 1, 2, 3인 상태에서 일부 맞춘 추측값
        val guess = "132"

        // When: play 메서드 호출
        val result = baseball.play(guess)

        // Then: 1 strike, 2 balls 결과 확인
        assertEquals(BallCount(strikes = 1, balls = 2), result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun playThrowsExceptionForInvalidInput() {
        // Given: 중복된 숫자가 포함된 잘못된 추측값
        val guess = "113"

        // When: play 메서드 호출 시
        baseball.play(guess)

        // Then: IllegalArgumentException 예외 발생
    }
}
