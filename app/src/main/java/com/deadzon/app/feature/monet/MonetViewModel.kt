package com.deadzon.app.feature.monet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deadzon.app.core.theme.ThemeSync
import com.deadzon.app.data.overlay.MonetOverlayRegistry
import com.deadzon.app.data.prefs.MonetPreferences
import com.deadzon.app.data.prefs.StoredMonetState
import com.deadzon.app.domain.monet.MonetController
import com.deadzon.app.domain.monet.MonetUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MonetViewModel @Inject constructor(
    private val prefs: MonetPreferences,
    private val controller: MonetController
) : ViewModel() {
    private val _uiState = MutableStateFlow(MonetUiState())
    val uiState: StateFlow<MonetUiState> = _uiState.asStateFlow()

    val targets = MonetOverlayRegistry.targets
    val presets = MonetOverlayRegistry.presets

    init {
        viewModelScope.launch {
            prefs.state.collect { stored ->
                bindStored(stored)
            }
        }
        refreshRootStatus()
    }

    fun refreshRootStatus() {
        viewModelScope.launch {
            _uiState.update { it.copy(rootAvailable = controller.hasRoot()) }
        }
    }

    fun requestRootFromOnboarding() {
        viewModelScope.launch {
            _uiState.update { it.copy(busy = true, lastResult = null) }
            val hasRoot = controller.hasRoot()
            prefs.update { it.copy(rootGateCompleted = hasRoot) }
            _uiState.update {
                it.copy(
                    busy = false,
                    rootAvailable = hasRoot,
                    rootGateCompleted = hasRoot,
                    lastResult = if (hasRoot) "Root access granted" else "Root access is required to continue"
                )
            }
        }
    }

    fun onMonetToggle(value: Boolean) = persist { copy(monetEnabled = value) }
    fun onApplyToAppToggle(value: Boolean) = persist { copy(applyToApp = value) }
    fun onSearchChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onPresetSelect(id: String) = persist {
        val preset = presets.firstOrNull { it.id == id }
        preset?.let { ThemeSync.updateSeed(it.seedColor) }
        copy(presetId = id)
    }

    fun toggleTarget(key: String) = persist {
        copy(selectedTargets = selectedTargets.toMutableSet().apply {
            if (contains(key)) remove(key) else add(key)
        })
    }

    fun selectAll() = persist { copy(selectedTargets = targets.map { it.preferenceKey }.toSet()) }
    fun clearAll() = persist { copy(selectedTargets = emptySet()) }

    fun apply(restartSystemUi: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(busy = true, lastResult = null) }
            val state = _uiState.value
            val result = controller.apply(state.selectedTargets, state.monetEnabled, restartSystemUi)
            _uiState.update {
                it.copy(
                    busy = false,
                    logs = (result.logs + it.logs).takeLast(200),
                    lastResult = if (result.success) "Apply completed" else "Apply failed"
                )
            }
        }
    }

    fun reset() {
        viewModelScope.launch {
            _uiState.update { it.copy(busy = true, lastResult = null) }
            val result = controller.reset()
            prefs.update {
                StoredMonetState(
                    monetEnabled = false,
                    applyToApp = true,
                    presetId = "stock",
                    selectedTargets = emptySet(),
                    rootGateCompleted = it.rootGateCompleted
                )
            }
            _uiState.update {
                it.copy(
                    busy = false,
                    logs = (result.logs + it.logs).takeLast(200),
                    lastResult = "Reset completed"
                )
            }
        }
    }

    private fun persist(transform: StoredMonetState.() -> StoredMonetState) {
        viewModelScope.launch { prefs.update(transform) }
    }

    private fun bindStored(stored: StoredMonetState) {
        val preset = presets.firstOrNull { it.id == stored.presetId } ?: presets.first()
        ThemeSync.updateSeed(preset.seedColor)
        _uiState.update {
            it.copy(
                monetEnabled = stored.monetEnabled,
                applyToApp = stored.applyToApp,
                selectedPresetId = stored.presetId,
                selectedTargets = stored.selectedTargets,
                rootGateCompleted = stored.rootGateCompleted
            )
        }
    }
}
