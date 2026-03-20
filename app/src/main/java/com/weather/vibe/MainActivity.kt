package com.weather.vibe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.weather.vibe.feature.home.HomeScreen
import com.weather.vibe.ui.theme.WeatherVibeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherVibeTheme {
                HomeScreen()
            }
        }
    }
}
