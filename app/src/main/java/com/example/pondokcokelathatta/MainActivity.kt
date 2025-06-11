package com.example.pondokcokelathatta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Aktifkan layout edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            com.example.pondokcokelathatta.ui.screens.PolattaScreen()
        }
    }
}