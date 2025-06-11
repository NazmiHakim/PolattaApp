package com.example.pondokcokelathatta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.pondokcokelathatta.ui.screens.PolattaScreen()
        }
    }
}
