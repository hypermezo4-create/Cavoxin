package com.deadzon.app.core.theme

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ThemeSync {
    private val _seed = MutableStateFlow(Color(0xFF7C4DFF))
    val seed: StateFlow<Color> = _seed

    fun updateSeed(color: Color) {
        _seed.value = color
    }
}
