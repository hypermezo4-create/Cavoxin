package com.deadzon.app.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
                SectionTitle("Settings", "DeadZon preferences and app behavior")
                Text("This app centralizes ROM customizations in one premium interface.")
            }
            GlassCard {
                Text("Behavior", style = MaterialTheme.typography.titleMedium)
                Text("• Root is requested only for privileged actions.")
                Text("• Monet is available under Tools.")
                Text("• Module pages map to existing ROM preference keys.")
            }
        }
    }
}
