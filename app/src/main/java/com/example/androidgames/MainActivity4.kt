package com.example.androidgames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidgames.ui.theme.AndroidgamesTheme

class MainActivity4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidgamesTheme {
                TicTacToeGame()
            }
        }
    }
}

@Composable
private fun TicTacToeGame() {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }
    var isDraw by remember { mutableStateOf(false) }

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
            GameTopBar()

            GameStatus(currentPlayer, winner, isDraw)

            GameBoard(
                board = board,
                onCellClick = { index ->
                    if (board[index].isEmpty() && winner == null && !isDraw) {
                        val newBoard = board.toMutableList()
                        newBoard[index] = currentPlayer
                        board = newBoard

                        when {
                            checkWinner(newBoard, currentPlayer) -> winner = currentPlayer
                            newBoard.none { it.isEmpty() } -> isDraw = true
                            else -> currentPlayer = if (currentPlayer == "X") "O" else "X"
                        }
                    }
                }
            )

            if (winner != null || isDraw) {
                Button(
                    onClick = {
                        board = List(9) { "" }
                        currentPlayer = "X"
                        winner = null
                        isDraw = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Play Again")
                }
            }

            GameFooter()
        }
    }
}

@Composable
private fun GameTopBar() {
    Text(
        text = "CHILL GAMES",
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
private fun GameStatus(currentPlayer: String, winner: String?, isDraw: Boolean) {
    Text(
        text = when {
            winner != null -> "Player $winner Wins! 🎉"
            isDraw -> "It's a Draw! 🤝"
            else -> "Player $currentPlayer's Turn"
        },
        color = Color.White,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
private fun GameBoard(
    board: List<String>,
    onCellClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Black)
    ) {
        for (row in 0..2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (col in 0..2) {
                    GameCell(
                        value = board[row * 3 + col],
                        onClick = { onCellClick(row * 3 + col) }
                    )
                }
            }
        }
    }
}

@Composable
private fun GameCell(
    value: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .padding(4.dp)
            .border(2.dp, Color.White)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            color = when (value) {
                "X" -> Color(0xFF4CAF50) // Green
                "O" -> Color(0xFF2196F3) // Blue
                else -> Color.White
            },
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun GameFooter() {
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

private fun checkWinner(board: List<String>, player: String): Boolean {
    val winningCombinations = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
        listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Columns
        listOf(0, 4, 8), listOf(2, 4, 6) // Diagonals
    )

    return winningCombinations.any { combination ->
        combination.all { position -> board[position] == player }
    }
}