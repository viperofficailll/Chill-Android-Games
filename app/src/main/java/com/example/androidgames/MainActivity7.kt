package com.example.androidgames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidgames.ui.theme.AndroidgamesTheme
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

class MainActivity7 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidgamesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = 80.dp), // Space for footer
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AboutUsContent()
                        }
                        Footer(
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AboutUsContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "About Us",
            color = Color(0xFFFF0000), // Red color
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Main Content Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("This is a project made by ")
                        withStyle(style = SpanStyle(
                            color = Color(0xFF00E5FF),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )) {
                            append("Sandesh Pokhrel")
                        }
                        append(", ")
                        withStyle(style = SpanStyle(
                            color = Color(0xFF00E5FF),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )) {
                            append("Swopnil Regmi")
                        }
                        append(" and ")
                        withStyle(style = SpanStyle(
                            color = Color(0xFF00E5FF),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )) {
                            append("Samip Sangroula")
                        }
                    },
                    color = Color.White,
                    fontSize = 18.sp,
                    lineHeight = 28.sp
                )

                Text(
                    text = "Founders of ChillMaCoding (a less talked about startup)",
                    color = Color(0xFFAAAAAA),
                    fontSize = 16.sp
                )

                Text(
                    text = "Sandesh Pokhrel is a Full Stack Developer",
                    color = Color.White,
                    fontSize = 16.sp
                )

                Text(
                    text = "Swopnil Regmi is an Unreal Engine Game Developer",
                    color = Color.White,
                    fontSize = 16.sp
                )

                Text(
                    text = "Samip Sangroula is an Unreal Engine Game Developer",
                    color = Color.White,
                    fontSize = 16.sp
                )


            }
        }

        // Links Section
        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .background(Color(0xFF1A1A1A), RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LinkButton(
                text = "Sandesh Pokhrel",
                url = "https://pokhrelsandesh.com.np"
            )
            LinkButton(
                text = "ChillMaCoding",
                url = "https://x.com/ChillMaCoding"
            )
            LinkButton(
                text = "YouTube Channel",
                url = "https://www.youtube.com/@ChillMaCoding"
            )
            LinkButton(
                text = "Swopnil Regmi",
                url = "https://x.com/Swopnil71"
            )
            LinkButton(
                text = "Samip Sangroula",
                url = "https://x.com/SamipSangroula"
            )
        }
    }
}

@Composable
fun LinkButton(text: String, url: String) {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00E5FF)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A))
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {


            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "© 2024  ChillMaCoding All rights reserved",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}