package com.deadzon.app.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deadzon.app.feature.dashboard.DashboardScreen
import com.deadzon.app.feature.monet.MonetScreen
import com.deadzon.app.feature.settings.SettingsScreen

@Composable
fun DeadZonApp() {
    val navController = rememberNavController()
    val tabs = listOf(
        Screen.Dashboard,
        Screen.Monet,
        Screen.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
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
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = Screen.Dashboard.route, modifier = Modifier.padding(paddingValues)) {
            composable(Screen.Dashboard.route) { DashboardScreen(navController) }
            composable(Screen.Monet.route) { MonetScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}
