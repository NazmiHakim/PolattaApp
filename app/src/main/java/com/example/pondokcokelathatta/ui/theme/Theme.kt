package com.example.pondokcokelathatta.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.pondokcokelathatta.ui.theme.BrownAccent
import com.pondokcokelathatta.ui.theme.BrownLight
import com.pondokcokelathatta.ui.theme.BrownPrimary
import com.pondokcokelathatta.ui.theme.OrangeAccent
import com.pondokcokelathatta.ui.theme.TextPrimary
import com.pondokcokelathatta.ui.theme.WhiteCream

private val LightColorScheme = lightColorScheme(
    primary = BrownPrimary,
    secondary = BrownAccent,
    tertiary = OrangeAccent,
    background = BrownLight,
    surface = WhiteCream,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
)

@Composable
fun PondokCokelatHattaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme // Saat ini hanya mendukung light theme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}