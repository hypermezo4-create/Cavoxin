package com.deadzon.app.feature.monet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.deadzon.app.core.ui.ColorPreviewRow
import com.deadzon.app.core.ui.PresetChips
import com.deadzon.app.core.ui.QuickSwitch
import com.deadzon.app.core.ui.RootStatusCard
import com.deadzon.app.core.ui.TargetRow

@Composable
fun MonetScreen(vm: MonetViewModel = hiltViewModel()) {
    val state by vm.uiState.collectAsState()
    val selectedPreset = vm.presets.firstOrNull { it.id == state.selectedPresetId } ?: vm.presets.first()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Monet Control", style = MaterialTheme.typography.headlineMedium)
        RootStatusCard(rootAvailable = state.rootAvailable)
        QuickSwitch("Master Monet switch", state.monetEnabled, vm::onMonetToggle)
        QuickSwitch("Apply palette to Dead Zon", state.applyToApp, vm::onApplyToAppToggle)
        PresetChips(vm.presets, state.selectedPresetId, vm::onPresetSelect)
        ColorPreviewRow(selectedPreset)

        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = vm::onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search targets") }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = vm::selectAll) { Text("Select All") }
            Button(onClick = vm::clearAll) { Text("Clear All") }
            Button(onClick = { vm.apply(restartSystemUi = false) }) { Text("Apply") }
            Button(onClick = vm::reset) { Text("Reset") }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.apply(restartSystemUi = true) }) { Text("Apply + Restart SystemUI") }
            if (state.busy) CircularProgressIndicator()
        }

        state.lastResult?.let { Text(it, color = MaterialTheme.colorScheme.primary) }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val filtered = vm.targets.filter {
                it.title.contains(state.searchQuery, true) || it.overlayPackage.contains(state.searchQuery, true)
            }
            items(filtered, key = { it.preferenceKey }) { target ->
                TargetRow(
                    target = target,
                    checked = target.preferenceKey in state.selectedTargets,
                    onToggle = { vm.toggleTarget(target.preferenceKey) }
                )
            }
        }
    }
}
