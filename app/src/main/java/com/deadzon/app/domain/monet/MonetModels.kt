package com.deadzon.app.domain.monet

import androidx.compose.ui.graphics.Color

enum class TargetCategory { CORE_SYSTEM, MIUI, MEDIA, UTILITIES }

data class MonetPreset(
    val id: String,
    val name: String,
    val seedColor: Color,
    val preview: List<Color>
)

data class MonetTarget(
    val preferenceKey: String,
    val title: String,
    val overlayPackage: String,
    val category: TargetCategory,
    val isSelectedByDefault: Boolean = true
)

data class RootCommandResult(
    val command: String,
    val exitCode: Int,
    val stdout: String,
    val stderr: String
)

data class ApplyResult(
    val success: Boolean,
    val applied: List<String>,
    val disabled: List<String>,
    val missing: List<String>,
    val logs: List<String>
)

data class MonetUiState(
    val monetEnabled: Boolean = true,
    val applyToApp: Boolean = true,
    val selectedPresetId: String = "stock",
    val searchQuery: String = "",
    val selectedTargets: Set<String> = emptySet(),
    val rootAvailable: Boolean = false,
    val busy: Boolean = false,
    val logs: List<String> = emptyList(),
    val lastResult: String? = null
)
