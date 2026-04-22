package com.deadzon.app.feature.modules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deadzon.app.app.Screen
import com.deadzon.app.core.ui.DeadZonBackground
import com.deadzon.app.core.ui.GlassCard
import com.deadzon.app.core.ui.SectionTitle

@Composable
fun ModulesScreen(navController: NavController) {
    DeadZonBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionTitle("Modules", "ROM controls organized in one polished hub")
            ModuleEntry("About Phone", "Header style, blur and device profile") { navController.navigate(Screen.AboutPhone.route) }
            ModuleEntry("Notifications", "Icons, privacy, lockscreen behavior") { navController.navigate(Screen.Notifications.route) }
            ModuleEntry("Notification Animations", "Animation sets and duration") { navController.navigate(Screen.NotificationAnimations.route) }
            ModuleEntry("Lockscreen", "Charging and lockscreen info") { navController.navigate(Screen.Lockscreen.route) }
            ModuleEntry("Control Center / Quick Settings", "Tiles, rows and color controls") { navController.navigate(Screen.ControlCenter.route) }
            ModuleEntry("Status Bar", "Coming soon", enabled = false) { }
            ModuleEntry("Extra / Misc", "Coming soon", enabled = false) { }
        }
    }
}

@Composable
private fun ModuleEntry(title: String, subtitle: String, enabled: Boolean = true, onClick: () -> Unit) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick)
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun ModuleSectionScreen(section: FeatureSection, vm: ModulesViewModel = hiltViewModel()) {
    val state by vm.uiState.collectAsState()

    DeadZonBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SectionTitle(section.title, section.subtitle)
            state.message?.let { GlassCard { Text(it) } }
            section.options.forEach { option ->
                FeatureOptionCard(
                    option = option,
                    value = state.values[option.key].orEmpty(),
                    busy = state.busy,
                    onValue = { vm.setValue(option.key, it) },
                    onAction = { vm.triggerRootAction(option.key) }
                )
            }
        }
    }
}

@Composable
private fun FeatureOptionCard(
    option: FeatureOption,
    value: String,
    busy: Boolean,
    onValue: (String) -> Unit,
    onAction: () -> Unit
) {
    GlassCard {
        Text(option.title, style = MaterialTheme.typography.titleMedium)
        option.summary?.let { Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant) }
        when (option.type) {
            FeatureControlType.TOGGLE -> {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(if (value.toBoolean()) "Enabled" else "Disabled")
                    Switch(checked = value.toBoolean(), onCheckedChange = { onValue(it.toString()) })
                }
            }
            FeatureControlType.SLIDER -> {
                val current = value.toFloatOrNull() ?: 0f
                Text(current.toInt().toString())
                Slider(value = current, onValueChange = { onValue(it.toInt().toString()) }, valueRange = 0f..255f)
            }
            FeatureControlType.LIST -> ListPicker(value = value, options = option.choices, onValue = onValue)
            FeatureControlType.COLOR -> OutlinedTextField(value = value, onValueChange = onValue, label = { Text("HEX") }, modifier = Modifier.fillMaxWidth())
            FeatureControlType.ACTION -> {
                Button(onClick = onAction, enabled = !busy) {
                    if (busy) {
                        CircularProgressIndicator(strokeWidth = 2.dp)
                    } else {
                        Text(if (option.requiresRootAction) "Grant Root & Run" else "Run")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListPicker(value: String, options: List<String>, onValue: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text("Selection") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    expanded = false
                    onValue(option)
                })
            }
        }
    }
}
