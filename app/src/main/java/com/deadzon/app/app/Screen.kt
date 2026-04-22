package com.deadzon.app.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Home : Screen("home", "Home", Icons.Default.Dashboard)
    data object Modules : Screen("modules", "Modules", Icons.Default.GridView)
    data object Tools : Screen("tools", "Tools", Icons.Default.Extension)
    data object Settings : Screen("settings", "Settings", Icons.Default.Settings)

    data object Monet : Screen("monet", "Monet", Icons.Default.Palette)
    data object AboutPhone : Screen("modules/about_phone", "About Phone", Icons.Default.GridView)
    data object Notifications : Screen("modules/notifications", "Notifications", Icons.Default.GridView)
    data object NotificationAnimations : Screen("modules/notification_animations", "Notification Animations", Icons.Default.GridView)
    data object Lockscreen : Screen("modules/lockscreen", "Lockscreen", Icons.Default.GridView)
    data object ControlCenter : Screen("modules/control_center", "Control Center", Icons.Default.GridView)
}
