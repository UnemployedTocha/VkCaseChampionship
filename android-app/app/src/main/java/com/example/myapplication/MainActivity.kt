package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Здесь будет наша верстка Compose
            WelcomeScreen()
        }
    }
}

@Composable
fun WelcomeScreen() {
    Scaffold { padding ->
        Text(
            text = "Привет, Compose!",
            modifier = androidx.compose.ui.Modifier.padding(padding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}
