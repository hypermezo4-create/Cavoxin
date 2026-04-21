package com.deadzon.app.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deadzon.app.app.Screen
import com.deadzon.app.feature.monet.MonetViewModel

@Composable
fun DashboardScreen(navController: NavController, vm: MonetViewModel = hiltViewModel()) {
    val state by vm.uiState.collectAsState()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Dead Zon", style = MaterialTheme.typography.headlineLarge)
        Card(modifier = Modifier.padding(top = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Monet status: ${if (state.monetEnabled) "Enabled" else "Disabled"}")
                Text("Selected targets: ${state.selectedTargets.size}")
                Text("Active preset: ${state.selectedPresetId}")
            }
        }
        Button(onClick = { vm.apply(false) }) { Text("Quick Apply") }
        Button(onClick = { navController.navigate(Screen.Monet.route) }) { Text("Open Monet Screen") }
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Future modules", style = MaterialTheme.typography.titleMedium)
                Text("• Performance (coming soon)")
                Text("• Thermal profiles (coming soon)")
                Text("• Network tuning (coming soon)")
            }
        }
    }
}
