package com.deadzon.app.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable
fun DeadZonTheme(content: @Composable () -> Unit) {
    val seed by ThemeSync.seed.collectAsState()
    val dark = isSystemInDarkTheme()
    val colorScheme = if (dark) {
        darkColorScheme(
            primary = seed,
            secondary = seed.copy(alpha = 0.78f),
            background = Color.Black,
            surface = Color(0xFF080808),
            tertiary = Color(0xFFB71C1C)
        )
    } else {
        lightColorScheme(primary = seed)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
