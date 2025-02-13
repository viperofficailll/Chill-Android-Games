package com.example.androidgames

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidgames.ui.theme.AndroidgamesTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidgamesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Black
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        WelcomeSection()
                        NavigateButton()
                        FooterSection()
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeSection() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 64.dp)
    ) {
        Text(
            text = "Welcome to\n \nChill Android Games",
            color = Color.White,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.scale(scale)
        )

        Spacer(modifier = Modifier.height(24.dp))

        RotatingQuote()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RotatingQuote() {
    val quotes = listOf(
        "All defeats are psychological till death",
        "Money is the source of Respect",
        "Har Har Mahadev",
        "Right practice makes a man perfect",

    )

    var currentQuoteIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while(true) {
            delay(5000)
            currentQuoteIndex = (currentQuoteIndex + 1) % quotes.size
        }
    }

    AnimatedContent(
        targetState = currentQuoteIndex,
        transitionSpec = {
            fadeIn(animationSpec = tween(1000)) with
                    fadeOut(animationSpec = tween(1000))
        },
        label = ""
    ) { index ->
        Text(
            text = quotes[index],
            color = Color(0xFFE0E0E0),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
fun NavigateButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val color by infiniteTransition.animateValue(
        initialValue = Color(0xFF4CAF50),
        targetValue = Color(0xFF2196F3),
        typeConverter = ColorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Button(
        onClick = {
            val intent = Intent(context, MainActivity2::class.java)
            context.startActivity(intent)
        },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = modifier
            .padding(16.dp)
            .height(50.dp)
            .width(200.dp)
    ) {
        Text(
            "Explore Games",
            fontSize = 18.sp
        )
    }
}

@Composable
fun FooterSection() {
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

private object ColorConverter : TwoWayConverter<Color, AnimationVector4D> {
    override val convertFromVector: (AnimationVector4D) -> Color
        get() = { vector ->
            Color(vector.v1, vector.v2, vector.v3, vector.v4)
        }

    override val convertToVector: (Color) -> AnimationVector4D
        get() = { color ->
            AnimationVector4D(color.red, color.green, color.blue, color.alpha)
        }
}