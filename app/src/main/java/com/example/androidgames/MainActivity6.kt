package com.example.androidgames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

class MainActivity6 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnakeGame()
        }
    }
}

data class Point(val x: Int, val y: Int)

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

enum class GameState {
    READY, PLAYING, GAME_OVER
}

@Composable
fun SnakeGame() {
    var snake by remember { mutableStateOf(listOf(Point(10, 10))) }
    var food by remember { mutableStateOf(generateFood()) }
    var direction by remember { mutableStateOf(Direction.RIGHT) }
    var gameState by remember { mutableStateOf(GameState.READY) }
    var score by remember { mutableIntStateOf(0) }
    var highScore by remember { mutableIntStateOf(0) }
    var gameOverReason by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val gameSpeed by remember(score) {
        derivedStateOf {
            max(50L, 300L - (score * 10L))
        }
    }

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
            GameHeader(score, highScore)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color(0xFF1A1A1A))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {},
                            onDragStart = {
                                if (gameState == GameState.READY) {
                                    gameState = GameState.PLAYING
                                }
                            },
                            onDrag = { change, dragAmount ->
                                if (gameState == GameState.PLAYING) {
                                    change.consume()
                                    val (x, y) = dragAmount
                                    if (abs(x) > abs(y)) {
                                        if (x > 0 && direction != Direction.LEFT) {
                                            direction = Direction.RIGHT
                                        } else if (x < 0 && direction != Direction.RIGHT) {
                                            direction = Direction.LEFT
                                        }
                                    } else {
                                        if (y > 0 && direction != Direction.UP) {
                                            direction = Direction.DOWN
                                        } else if (y < 0 && direction != Direction.DOWN) {
                                            direction = Direction.UP
                                        }
                                    }
                                }
                            }
                        )
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val cellSize = size.width / 20
                    drawBoundaries(cellSize)
                    snake.forEachIndexed { index, point ->
                        drawSnakeSegment(point, cellSize, index, snake.size)
                    }
                    drawFood(food, cellSize)
                }

                if (gameState == GameState.GAME_OVER) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(Color(0xCC000000))
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = gameOverReason,
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Score: $score",
                            color = Color.White,
                            fontSize = 20.sp
                        )

                        if (score >= highScore) {
                            Text(
                                text = "New High Score!",
                                color = Color(0xFFFFD700),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                snake = listOf(Point(10, 10))
                                direction = Direction.RIGHT
                                food = generateFood()
                                score = 0
                                gameState = GameState.READY
                                gameOverReason = ""
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Text("Play Again", fontSize = 18.sp)
                        }
                    }
                }

                if (gameState == GameState.READY) {
                    Text(
                        text = "Swipe to Start",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(Color(0x88000000))
                            .padding(16.dp)
                    )
                }
            }

            LaunchedEffect(gameState) {
                while (gameState == GameState.PLAYING) {
                    delay(gameSpeed)
                    val newSnake = moveSnake(snake, direction)

                    if (hasHitBoundary(newSnake.first())) {
                        gameState = GameState.GAME_OVER
                        if (score > highScore) highScore = score
                        gameOverReason = "Game Over!\nHit the boundary!"
                        continue
                    }

                    snake = newSnake

                    if (snake.first() == food) {
                        score++
                        snake = growSnake(snake, direction)
                        food = generateFood()
                    }

                    if (hasCollision(snake)) {
                        gameState = GameState.GAME_OVER
                        if (score > highScore) highScore = score
                        gameOverReason = "Game Over!\nHit yourself!"
                    }
                }
            }

            GameFooter(gameSpeed, gameState)
        }
    }
}

@Composable
private fun GameHeader(score: Int, highScore: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SNAKE GAME",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Score: $score",
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = "High Score: $highScore",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun GameFooter(gameSpeed: Long, gameState: GameState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        if (gameState == GameState.PLAYING) {
            Text(
                text = "Speed: ${(1000L/gameSpeed).toInt()} moves/sec",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
        Text(
            text = when(gameState) {
                GameState.READY -> "Swipe anywhere to start"
                GameState.PLAYING -> "Swipe to change direction"
                GameState.GAME_OVER -> "Game Over"
            },
            color = Color.Gray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "© 1914 ChillMaCoding All rights reserved",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

private fun DrawScope.drawSnakeSegment(point: Point, cellSize: Float, index: Int, snakeLength: Int) {
    val x = point.x * cellSize
    val y = point.y * cellSize

    // Draw main body segment
    drawCircle(
        color = Color(0xFF4CAF50),
        radius = cellSize * 0.4f,
        center = Offset(x + cellSize * 0.5f, y + cellSize * 0.5f)
    )

    // Draw pattern dots
    if (index % 2 == 0) {
        drawCircle(
            color = Color(0xFF388E3C),
            radius = cellSize * 0.15f,
            center = Offset(x + cellSize * 0.5f, y + cellSize * 0.5f)
        )
    }
}

private fun DrawScope.drawFood(food: Point, cellSize: Float) {
    val x = food.x * cellSize
    val y = food.y * cellSize

    // Draw food with a glowing effect
    drawCircle(
        color = Color(0xFFFF4444),
        radius = cellSize * 0.35f,
        center = Offset(x + cellSize * 0.5f, y + cellSize * 0.5f)
    )
    drawCircle(
        color = Color(0x66FF4444),
        radius = cellSize * 0.45f,
        center = Offset(x + cellSize * 0.5f, y + cellSize * 0.5f)
    )
}

private fun DrawScope.drawBoundaries(cellSize: Float) {
    for (i in 0..19) {
        // Draw fire effect boundaries
        val fireColors = listOf(
            Color(0xFFFF5722),
            Color(0xFFFF9800),
            Color(0x66FF5722)
        )

        fireColors.forEachIndexed { index, color ->
            val offset = index * (cellSize * 0.1f)

            // Top and bottom boundaries
            drawRect(
                color = color,
                topLeft = Offset(i * cellSize, offset),
                size = Size(cellSize, cellSize - offset)
            )
            drawRect(
                color = color,
                topLeft = Offset(i * cellSize, 19 * cellSize + offset),
                size = Size(cellSize, cellSize - offset)
            )

            // Left and right boundaries
            drawRect(
                color = color,
                topLeft = Offset(offset, i * cellSize),
                size = Size(cellSize - offset, cellSize)
            )
            drawRect(
                color = color,
                topLeft = Offset(19 * cellSize + offset, i * cellSize),
                size = Size(cellSize - offset, cellSize)
            )
        }
    }
}

private fun moveSnake(snake: List<Point>, direction: Direction): List<Point> {
    val head = snake.first()
    val newHead = when (direction) {
        Direction.UP -> Point(head.x, head.y - 1)
        Direction.DOWN -> Point(head.x, head.y + 1)
        Direction.LEFT -> Point(head.x - 1, head.y)
        Direction.RIGHT -> Point(head.x + 1, head.y)
    }
    return listOf(newHead) + snake.dropLast(1)
}

private fun growSnake(snake: List<Point>, direction: Direction): List<Point> {
    val head = snake.first()
    val newHead = when (direction) {
        Direction.UP -> Point(head.x, head.y - 1)
        Direction.DOWN -> Point(head.x, head.y + 1)
        Direction.LEFT -> Point(head.x - 1, head.y)
        Direction.RIGHT -> Point(head.x + 1, head.y)
    }
    return listOf(newHead) + snake
}

private fun generateFood(): Point {
    return Point(
        Random.nextInt(1, 19),
        Random.nextInt(1, 19)
    )
}

private fun hasHitBoundary(point: Point): Boolean {
    return point.x <= 0 || point.x >= 19 || point.y <= 0 || point.y >= 19
}

private fun hasCollision(snake: List<Point>): Boolean {
    val head = snake.first()
    return snake.drop(1).contains(head)
}