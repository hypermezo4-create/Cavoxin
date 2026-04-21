package com.deadzon.app.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deadzon.app.core.ui.BottomActionBar
import com.deadzon.app.core.ui.DeadZonBackground
import com.deadzon.app.core.ui.DeadZonLogoMark
import com.deadzon.app.core.ui.GlassCard
import com.deadzon.app.core.ui.SectionTitle
import com.deadzon.app.feature.dashboard.DashboardScreen
import com.deadzon.app.feature.monet.MonetScreen
import com.deadzon.app.feature.monet.MonetViewModel
import com.deadzon.app.feature.settings.SettingsScreen

@Composable
fun DeadZonApp(vm: MonetViewModel = hiltViewModel()) {
    val state by vm.uiState.collectAsState()

    if (!state.rootGateCompleted) {
        RootGateScreen(
            busy = state.busy,
            rootAvailable = state.rootAvailable,
            message = state.lastResult,
            onRefresh = vm::refreshRootStatus,
            onRequest = vm::requestRootFromOnboarding
        )
        return
    }

    val navController = rememberNavController()
    val tabs = listOf(Screen.Dashboard, Screen.Monet, Screen.Settings)

    DeadZonBackground {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                BottomActionBar {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        tabs.forEach { screen ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = { navController.navigate(screen.route) },
                                icon = { Icon(screen.icon, contentDescription = screen.title) },
                                label = { Text(screen.title) }
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Dashboard.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                composable(Screen.Dashboard.route) { DashboardScreen(navController) }
                composable(Screen.Monet.route) { MonetScreen() }
                composable(Screen.Settings.route) { SettingsScreen() }
            }
        }
    }
}

@Composable
private fun RootGateScreen(
    busy: Boolean,
    rootAvailable: Boolean,
    message: String?,
    onRefresh: () -> Unit,
    onRequest: () -> Unit
) {
    DeadZonBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            GlassCard {
                DeadZonLogoMark(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(8.dp))
                SectionTitle(
                    title = "Root Access Required",
                    subtitle = "Dead Zon needs root to control Monet overlays internally"
                )
                Text(if (rootAvailable) "Root detected. Continue." else "Tap request to trigger your root manager prompt.")
                message?.let { Text(it, color = Color(0xFFFFA0A0)) }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = onRefresh, modifier = Modifier.weight(1f)) { Text("Refresh") }
                    Button(
                        onClick = onRequest,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB91010))
                    ) {
                        if (busy) CircularProgressIndicator(strokeWidth = 2.dp, color = Color.White) else Text("Request Root")
                    }
                }
            }
        }
    }
}
