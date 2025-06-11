package com.example.pondokcokelathatta.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Skema warna terang untuk aplikasi
private val LightColorScheme = lightColorScheme(
    primary = BrownPrimary,
    secondary = BrownAccent,
    tertiary = OrangeAccent,
    background = Color.White, // Latar belakang diubah menjadi putih
    surface = Color.White,    // Surface diubah menjadi putih
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun PondokCokelatHattaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme // Saat ini hanya mendukung tema terang
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Baris di bawah ini tidak lagi diperlukan karena WindowCompat.setDecorFitsSystemWindows
            // window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}