package com.example.androidgames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidgames.ui.theme.AndroidgamesTheme
import kotlin.random.Random

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidgamesTheme {
                NumberGuessingGame()
            }
        }
    }
}

@Composable
private fun NumberGuessingGame() {
    var targetNumber by remember { mutableIntStateOf(Random.nextInt(1, 101)) }
    var userGuess by remember { mutableStateOf("") }
    var remainingGuesses by remember { mutableIntStateOf(10) }
    var gameMessage by remember { mutableStateOf("Guess a number between 1 and 100!") }
    var previousGuesses by remember { mutableStateOf(listOf<Int>()) }
    var gameOver by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            NumberGameTopBar()

            NumberGameContent(
                userGuess = userGuess,
                onGuessChange = { if (it.length <= 3) userGuess = it.filter { char -> char.isDigit() } },
                gameMessage = gameMessage,
                remainingGuesses = remainingGuesses,
                previousGuesses = previousGuesses,
                gameOver = gameOver,
                onGuessSubmit = {
                    val guess = userGuess.toIntOrNull()
                    if (guess != null && guess in 1..100) {
                        remainingGuesses--
                        previousGuesses = previousGuesses + guess
                        userGuess = ""

                        when {
                            guess == targetNumber -> {
                                gameMessage = "🎉 Congratulations! You won! The number was $targetNumber"
                                gameOver = true
                            }
                            remainingGuesses == 0 -> {
                                gameMessage = "Game Over! The number was $targetNumber"
                                gameOver = true
                            }
                            guess < targetNumber -> gameMessage = "Try higher!"
                            else -> gameMessage = "Try lower!"
                        }
                    } else {
                        gameMessage = "Please enter a valid number between 1 and 100"
                    }
                },
                onPlayAgain = {
                    targetNumber = Random.nextInt(1, 101)
                    remainingGuesses = 10
                    gameMessage = "Guess a number between 1 and 100!"
                    previousGuesses = listOf()
                    gameOver = false
                }
            )

            NumberGameFooter()
        }
    }
}

@Composable
private fun NumberGameTopBar() {
    Text(
        text = "CHILL GUESS",
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        color = Color.White,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun NumberGameContent(
    userGuess: String,
    onGuessChange: (String) -> Unit,
    gameMessage: String,
    remainingGuesses: Int,
    previousGuesses: List<Int>,
    gameOver: Boolean,
    onGuessSubmit: () -> Unit,
    onPlayAgain: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = gameMessage,
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!gameOver) {
            OutlinedTextField(
                value = userGuess,
                onValueChange = onGuessChange,
                label = { Text("Enter your guess", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onGuessSubmit,
                enabled = userGuess.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Submit Guess")
            }

            Text(
                text = "Remaining Guesses: $remainingGuesses",
                color = Color.White,
                fontSize = 18.sp
            )

            if (previousGuesses.isNotEmpty()) {
                Text(
                    text = "Previous Guesses: ${previousGuesses.joinToString(", ")}",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        if (gameOver) {
            Button(
                onClick = onPlayAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Play Again")
            }
        }
    }
}

@Composable
private fun NumberGameFooter() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "© 1914 ChillMaCoding All rights reserved",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}