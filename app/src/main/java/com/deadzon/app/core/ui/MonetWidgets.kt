package com.deadzon.app.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.deadzon.app.domain.monet.MonetPreset
import com.deadzon.app.domain.monet.MonetTarget

@Composable
fun RootStatusCard(rootAvailable: Boolean) {
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF111111))) {
        Text(
            text = if (rootAvailable) "Root status: available" else "Root status: unavailable",
            modifier = Modifier.padding(16.dp),
            color = if (rootAvailable) Color(0xFF66BB6A) else Color(0xFFEF5350)
        )
    }
}

@Composable
fun PresetChips(presets: List<MonetPreset>, selectedId: String, onSelect: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        presets.forEach { preset ->
            FilterChip(
                selected = preset.id == selectedId,
                onClick = { onSelect(preset.id) },
                label = { Text(preset.name) }
            )
        }
    }
}

@Composable
fun TargetRow(target: MonetTarget, checked: Boolean, onToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F0F0F)),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(target.title, style = MaterialTheme.typography.titleMedium)
                Text(target.overlayPackage, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Checkbox(checked = checked, onCheckedChange = { onToggle() })
        }
    }
}

@Composable
fun QuickSwitch(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF101010))) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title)
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
fun ColorPreviewRow(preset: MonetPreset) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        preset.preview.forEach {
            androidx.compose.foundation.Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(it, RoundedCornerShape(50))
            )
        }
    }
}
