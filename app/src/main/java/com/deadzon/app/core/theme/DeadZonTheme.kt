package com.deadzon.app.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable
fun DeadZonTheme(content: @Composable () -> Unit) {
    val seed by ThemeSync.seed.collectAsState()
    val colorScheme = darkColorScheme(
        primary = seed,
        onPrimary = Color.White,
        secondary = seed.copy(alpha = 0.82f),
        onSecondary = Color.White,
        tertiary = Color(0xFFFF4A4A),
        background = Color(0xFF020202),
        surface = Color(0xFF090909),
        surfaceVariant = Color(0xFF171010),
        onSurface = Color(0xFFF2F2F2),
        onSurfaceVariant = Color(0xFFB4A7A7)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
