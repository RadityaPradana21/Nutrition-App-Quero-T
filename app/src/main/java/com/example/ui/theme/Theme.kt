package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = OliveGreen,
    secondary = CreamGold,
    tertiary = WarmOrange,
    background = Color(0xFF1E1C1A), // Sleek, dark brown organic tone
    surface = Color(0xFF2C2621),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = BgCream,
    onSurface = BgCream
)

private val LightColorScheme = lightColorScheme(
    primary = OliveGreen,
    secondary = CreamGold,
    tertiary = WarmOrange,
    background = BgCream,
    surface = SurfaceLight,
    onPrimary = Color.White,
    onSecondary = DarkBrownText,
    onBackground = DarkBrownText,
    onSurface = DarkBrownText
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep true brand identity active
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
