package com.deadzon.app.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
    GlassCard {
        SectionTitle("Root Access", if (rootAvailable) "Granted" else "Required")
        Text(
            text = if (rootAvailable) "Overlay operations are ready." else "Grant root to continue using Dead Zon modules.",
            color = if (rootAvailable) Color(0xFF7DFF9A) else Color(0xFFFF8A8A)
        )
    }
}

@Composable
fun PresetChips(presets: List<MonetPreset>, selectedId: String, onSelect: (String) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(presets) { preset ->
            FilterChip(
                selected = preset.id == selectedId,
                onClick = { onSelect(preset.id) },
                label = { Text(preset.name) },
                border = CardDefaults.outlinedCardBorder(),
            )
        }
    }
}

@Composable
fun TargetRow(target: MonetTarget, checked: Boolean, onToggle: () -> Unit) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(target.title, style = MaterialTheme.typography.titleMedium)
                Text(target.overlayPackage, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Checkbox(checked = checked, onCheckedChange = { onToggle() })
        }
    }
}

@Composable
fun QuickSwitch(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    GlassCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
fun ColorPreviewRow(preset: MonetPreset) {
    GlassCard {
        Text("Palette Preview", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            preset.preview.forEach {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(it, RoundedCornerShape(50))
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(Color(0x66FFFFFF), RoundedCornerShape(99.dp)))
    }
}
