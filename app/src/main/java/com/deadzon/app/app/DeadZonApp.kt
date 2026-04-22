package com.deadzon.app.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
import com.deadzon.app.core.ui.BottomActionBar
import com.deadzon.app.core.ui.DeadZonBackground
import com.deadzon.app.feature.dashboard.DashboardScreen
import com.deadzon.app.feature.modules.ModuleCatalog
import com.deadzon.app.feature.modules.ModuleSectionScreen
import com.deadzon.app.feature.modules.ModulesScreen
import com.deadzon.app.feature.monet.MonetScreen
import com.deadzon.app.feature.settings.SettingsScreen
import com.deadzon.app.feature.tools.ToolsScreen

@Composable
fun DeadZonApp() {
    val navController = rememberNavController()
    val tabs = listOf(Screen.Home, Screen.Modules, Screen.Tools, Screen.Settings)

    DeadZonBackground {
        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
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
                startDestination = Screen.Home.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                composable(Screen.Home.route) { DashboardScreen(navController) }
                composable(Screen.Modules.route) { ModulesScreen(navController) }
                composable(Screen.Tools.route) { ToolsScreen(navController) }
                composable(Screen.Settings.route) { SettingsScreen() }
                composable(Screen.Monet.route) { MonetScreen() }
                composable(Screen.AboutPhone.route) { ModuleSectionScreen(ModuleCatalog.aboutPhone) }
                composable(Screen.Notifications.route) { ModuleSectionScreen(ModuleCatalog.notifications) }
                composable(Screen.NotificationAnimations.route) { ModuleSectionScreen(ModuleCatalog.notificationAnimations) }
                composable(Screen.Lockscreen.route) { ModuleSectionScreen(ModuleCatalog.lockscreen) }
                composable(Screen.ControlCenter.route) { ModuleSectionScreen(ModuleCatalog.controlCenter) }
            }
        }
    }
}
