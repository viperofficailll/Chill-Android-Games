package com.example.androidgames

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidgames.ui.theme.AndroidgamesTheme

class MainActivity2 : ComponentActivity() {
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
                        GameSelectionHeader()
                        GameSelectionButtons()
                        GameSelectionFooter()
                    }
                }
            }
        }
    }
}

@Composable
private fun GameSelectionHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 64.dp)
    ) {
        Text(
            text = "Choose Your Game",
            color = Color.White,
            fontSize = 28.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Select a game to start playing",
            color = Color(0xFFE0E0E0),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
private fun GameSelectionButtons() {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 32.dp)
    ) {
        Button(
            onClick = {
                val intent = Intent(context, MainActivity3::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text(
                "Guess The Number",
                fontSize = 18.sp
            )
        }


        Button(
            onClick = {
                val intent = Intent(context, MainActivity4::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text(
                "Tic Tac Toe",
                fontSize = 18.sp
            )
        }

        Button(
            onClick = {
                val intent = Intent(context, MainActivity6::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text(
                "Snake Game",
                fontSize = 18.sp
            )
        }
        Button(
            onClick = {
                val intent = Intent(context, MainActivity7::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000)),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text(
                "About Us",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun GameSelectionFooter() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "© 1914 ChillMaCodingAll rights reserved",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}