package com.deadzon.app.feature.tools

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.deadzon.app.app.Screen
import com.deadzon.app.core.ui.DeadZonBackground
import com.deadzon.app.core.ui.GlassCard
import com.deadzon.app.core.ui.SectionTitle

@Composable
fun ToolsScreen(navController: NavController) {
    DeadZonBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionTitle("Tools", "Action-driven utilities and overlays")
            GlassCard(modifier = Modifier.clickable { navController.navigate(Screen.Monet.route) }) {
                Text("Monet & Overlays", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text("Palette presets, target apps, apply/reset actions", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            GlassCard {
                Text("Root is requested only when you run privileged actions.")
                Text("No root prompt is shown at app launch.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("More tools coming soon", style = MaterialTheme.typography.titleMedium)
                Text("Status bar packs, icon packs, and quick repair actions.")
            }
        }
    }
}
