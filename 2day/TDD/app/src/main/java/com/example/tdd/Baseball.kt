package com.example.tdd

import com.example.tdd.answer.Baseball

// 테스트 케이스
// - 3자리 숫자 생성
// - 각 자리는 서로 다른 숫자
// - 각 자리는 0-9 사이의 숫자
class NumberGenerator {
    fun generate(): List<Int> {
        return listOf(1, 2, 3)
    }
}

// 테스트 케이스
// 자릿수, 숫자X, 중복인 경우 IllegalArgumentException
class InputValidator {
    fun validate(input: String): List<Int> {
        return listOf(1, 2, 3)
    }
}

data class BallCount(
    val strikes: Int,
    val balls: Int
)

// 테스트 케이스
// - strikes, balls 체크
class BallCounter {
    fun count(answer: List<Int>, guess: List<Int>): BallCount {
        return BallCount(0, 0)
    }
}

// 테스트 케이스
// - 정답과 다른 숫자 입력
// - 정답 입력
// - 잘못된 입력 -> 예외 발생
class Baseball(private val generator: NumberGenerator) {
    fun play(guess: String): BallCount {
        return BallCount(0, 0)
    }
}
