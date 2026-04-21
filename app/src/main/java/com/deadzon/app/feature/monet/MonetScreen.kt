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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.deadzon.app.core.ui.BottomActionBar
import com.deadzon.app.core.ui.ColorPreviewRow
import com.deadzon.app.core.ui.DeadZonBackground
import com.deadzon.app.core.ui.GlassCard
import com.deadzon.app.core.ui.PresetChips
import com.deadzon.app.core.ui.QuickSwitch
import com.deadzon.app.core.ui.RootStatusCard
import com.deadzon.app.core.ui.SectionTitle
import com.deadzon.app.core.ui.TargetRow

@Composable
fun MonetScreen(vm: MonetViewModel = hiltViewModel()) {
    val state by vm.uiState.collectAsState()

    DeadZonBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    SectionTitle("Monet Control", "Temporary redesign pass inspired by red glass mockup")
                }
                item { RootStatusCard(rootAvailable = state.rootAvailable) }
                item { QuickSwitch("Master Monet Switch", state.monetEnabled, vm::onMonetToggle) }
                item { QuickSwitch("Apply to Dead Zon App", state.applyToApp, vm::onApplyToAppToggle) }
                item {
                    GlassCard {
                        Text("Preset Palette", style = MaterialTheme.typography.titleMedium)
                        PresetChips(vm.presets, state.selectedPresetId, vm::onPresetSelect)
                    }
                }
                item {
                    val selectedPreset = vm.presets.firstOrNull { it.id == state.selectedPresetId } ?: vm.presets.first()
                    ColorPreviewRow(selectedPreset)
                }
                item {
                    GlassCard {
                        OutlinedTextField(
                            value = state.searchQuery,
                            onValueChange = vm::onSearchChange,
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Search target app / package") }
                        )
                    }
                }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = vm::selectAll, modifier = Modifier.weight(1f)) { Text("Select All") }
                        Button(onClick = vm::clearAll, modifier = Modifier.weight(1f)) { Text("Clear All") }
                    }
                }
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
                item {
                    state.lastResult?.let {
                        GlassCard { Text(it, color = MaterialTheme.colorScheme.primary) }
                    }
                }
            }

            BottomActionBar {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { vm.apply(false) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB91010))
                    ) { Text("Apply") }
                    Button(onClick = vm::reset, modifier = Modifier.weight(1f)) { Text("Reset") }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { vm.apply(true) }, modifier = Modifier.weight(1f)) {
                        Text("Apply + Restart SystemUI")
                    }
                    if (state.busy) CircularProgressIndicator(strokeWidth = 2.dp)
                }
            }
        }
    }
}
