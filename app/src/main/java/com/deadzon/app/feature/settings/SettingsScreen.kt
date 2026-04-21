package com.deadzon.app.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deadzon.app.core.ui.DeadZonBackground
import com.deadzon.app.core.ui.DeadZonLogoMark
import com.deadzon.app.core.ui.GlassCard
import com.deadzon.app.core.ui.SectionTitle

@Composable
fun SettingsScreen() {
    DeadZonBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GlassCard {
                DeadZonLogoMark()
                SectionTitle("Settings", "Temporary premium visual pass")
                Text("Dead Zon keeps Monet and overlay controls fully in-app without ThemePicker or ThemesStub runtime dependency.")
            }
            GlassCard {
                Text("Profiles", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Export Profile (placeholder)") }
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Import Profile (placeholder)") }
            }
            GlassCard {
                Text("About")
                Text("Version 1 visual redesign preview")
            }
        }
    }
}
