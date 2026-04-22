package com.deadzon.app.feature.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deadzon.app.core.root.RootShell
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ModuleUiState(
    val values: Map<String, String> = emptyMap(),
    val busy: Boolean = false,
    val message: String? = null
)

@HiltViewModel
class ModulesViewModel @Inject constructor(
    private val rootShell: RootShell
) : ViewModel() {
    private val defaults = (
        ModuleCatalog.aboutPhone.options +
            ModuleCatalog.notifications.options +
            ModuleCatalog.notificationAnimations.options +
            ModuleCatalog.lockscreen.options +
            ModuleCatalog.controlCenter.options
        ).associate { it.key to it.defaultValue }

    private val _uiState = MutableStateFlow(ModuleUiState(values = defaults))
    val uiState: StateFlow<ModuleUiState> = _uiState.asStateFlow()

    fun setValue(key: String, value: String) {
        _uiState.update { it.copy(values = it.values.toMutableMap().apply { put(key, value) }) }
    }

    fun triggerRootAction(actionKey: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(busy = true, message = null) }
            val hasRoot = rootShell.isRootAvailable()
            if (!hasRoot) {
                _uiState.update { it.copy(busy = false, message = "This action requires root access.") }
                return@launch
            }
            val result = when (actionKey) {
                "restart_systemui" -> rootShell.run("pkill -f com.android.systemui")
                else -> rootShell.run("true")
            }
            _uiState.update {
                it.copy(
                    busy = false,
                    message = if (result.exitCode == 0) "Action completed." else "Action failed: ${result.stderr.ifBlank { "unknown error" }}"
                )
            }
        }
    }
}
