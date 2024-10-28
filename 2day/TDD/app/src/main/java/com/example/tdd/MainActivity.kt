package com.example.tdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseballApp()
        }
    }
}

@Composable
fun BaseballApp() {
    val baseball = remember { Baseball(NumberGenerator()) }
    var guessInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<BallCount?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isGameWon by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("3자리 숫자를 맞춰보세요!", style = MaterialTheme.typography.headlineMedium)

        BasicTextField(
            value = guessInput,
            onValueChange = { guessInput = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                try {
                    errorMessage = null
                    val gameResult = baseball.play(guessInput)
                    result = gameResult

                    if (gameResult.strikes == 3) {
                        isGameWon = true
                    }
                } catch (e: IllegalArgumentException) {
                    errorMessage = e.message
                } finally {
                    guessInput = ""
                }
            },
            enabled = !isGameWon,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guess")
        }

        result?.let {
            Text(
                "Strikes: ${it.strikes}, Balls: ${it.balls}",
                style = MaterialTheme.typography.bodyMedium
            )
            if (isGameWon) {
                Text("정답입니다! 게임을 종료합니다.", color = MaterialTheme.colorScheme.primary)
            }
        }

        errorMessage?.let {
            Text("오류: $it", color = MaterialTheme.colorScheme.error)
        }
    }
}
