package com.deadzon.app.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deadzon.app.app.Screen
import com.deadzon.app.core.ui.DeadZonBackground
import com.deadzon.app.core.ui.DeadZonLogoMark
import com.deadzon.app.core.ui.GlassCard
import com.deadzon.app.core.ui.MetricChip
import com.deadzon.app.core.ui.SectionTitle
import com.deadzon.app.feature.monet.MonetViewModel

@Composable
fun DashboardScreen(navController: NavController, vm: MonetViewModel = hiltViewModel()) {
    val state by vm.uiState.collectAsState()

    DeadZonBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GlassCard {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DeadZonLogoMark()
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 8.dp)) {
                        Text("DEAD ZON")
                        Text("Premium Overlay Control", color = Color(0xFFB9A6A6))
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {
                    MetricChip(Icons.Default.Verified, "Monet", if (state.monetEnabled) "Enabled" else "Disabled")
                }
                Column(Modifier.weight(1f)) {
                    MetricChip(Icons.Default.Palette, "Preset", state.selectedPresetId.uppercase())
                }
            }

            MetricChip(Icons.Default.AutoFixHigh, "Selected Targets", state.selectedTargets.size.toString())

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { vm.apply(false) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB91010))
                ) { Text("Quick Apply") }
                Button(onClick = { navController.navigate(Screen.Monet.route) }, modifier = Modifier.weight(1f)) { Text("Open Monet") }
            }

            GlassCard {
                SectionTitle("Future Modules", "Temporary placeholders for this visual pass")
                Text("• Performance profiles")
                Text("• Thermal manager")
                Text("• Network tuning")
            }
        }
    }
}
