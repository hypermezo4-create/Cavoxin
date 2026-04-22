package com.deadzon.app.feature.modules

enum class FeatureControlType { TOGGLE, SLIDER, LIST, COLOR, ACTION }

data class FeatureOption(
    val key: String,
    val title: String,
    val summary: String? = null,
    val type: FeatureControlType,
    val defaultValue: String,
    val choices: List<String> = emptyList(),
    val requiresRootAction: Boolean = false
)

data class FeatureSection(
    val title: String,
    val subtitle: String? = null,
    val options: List<FeatureOption>
)

object ModuleCatalog {
    val aboutPhone = FeatureSection(
        title = "About Phone",
        subtitle = "Profile, header style, blur and background behavior",
        options = listOf(
            FeatureOption("my_device_name", "Device name", type = FeatureControlType.LIST, defaultValue = "Redmi Note 13 Pro+", choices = listOf("Redmi Note 13 Pro+", "Custom")),
            FeatureOption("mezo_version_name", "Version label", type = FeatureControlType.LIST, defaultValue = "3.0.6.0", choices = listOf("3.0.6.0", "Custom")),
            FeatureOption("horizontal_specs", "Enhanced specs layout", summary = "Xiaomi-style spec cards", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("change_mod", "About phone style", type = FeatureControlType.LIST, defaultValue = "Style 1", choices = listOf("Style 1", "Style 2", "Style 3")),
            FeatureOption("lock_backg", "Header background mode", type = FeatureControlType.LIST, defaultValue = "Blur", choices = listOf("Blur", "Transparent")),
            FeatureOption("lock_wall_about", "Header image source", type = FeatureControlType.LIST, defaultValue = "Lockscreen", choices = listOf("Lockscreen", "Wallpaper")),
            FeatureOption("show_weather_header", "Show weather in header", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("hide_mini", "Header logo mode", type = FeatureControlType.LIST, defaultValue = "Logo", choices = listOf("Logo", "Mini")),
            FeatureOption("mmu_habout_blur", "Header blur", type = FeatureControlType.SLIDER, defaultValue = "69"),
            FeatureOption("mmu_wabout_blur", "Mini wallpaper blur", type = FeatureControlType.SLIDER, defaultValue = "69"),
            FeatureOption("lock_infbackg", "Info card background", type = FeatureControlType.LIST, defaultValue = "Transparent", choices = listOf("Transparent", "Blur")),
            FeatureOption("lock_wall_infabout", "Info card image source", type = FeatureControlType.LIST, defaultValue = "Wallpaper", choices = listOf("Wallpaper", "Lockscreen"))
        )
    )

    val notifications = FeatureSection(
        title = "Notifications",
        subtitle = "General behavior, lockscreen, privacy and quality controls",
        options = listOf(
            FeatureOption("mezo_notif_icons_size", "Notification icon size", type = FeatureControlType.LIST, defaultValue = "35", choices = listOf("28", "32", "35", "40")),
            FeatureOption("bg_hide_lock_dnd", "Hide persistent DND notification", type = FeatureControlType.TOGGLE, defaultValue = "true"),
            FeatureOption("icon_mezo_app_color", "Colorize notification icons", type = FeatureControlType.TOGGLE, defaultValue = "true"),
            FeatureOption("status_bar_expand_top_notification", "Expand top notification", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("notif_mezo_sound", "Notification sound", type = FeatureControlType.TOGGLE, defaultValue = "true"),
            FeatureOption("mezo_notification", "Transparent notifications", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("show_notif_mezo_lock", "Show notifications on lockscreen", type = FeatureControlType.TOGGLE, defaultValue = "true"),
            FeatureOption("smart_notifications", "Smart notifications", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("disable_headsup", "Heads-up notification restriction", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("privacy_mezo_dot", "Privacy dot", type = FeatureControlType.TOGGLE, defaultValue = "true"),
            FeatureOption("enable_mezo_mock_locations", "Mock locations", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("lock_to_app_enabled", "Lock to app", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("vibrate_mezo_on", "Vibration and touch feedback", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("network_dealy", "Network delay optimization", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("edge_mode_enabled", "Edge mode", type = FeatureControlType.TOGGLE, defaultValue = "false")
        )
    )

    val notificationAnimations = FeatureSection(
        title = "Notification Animations",
        subtitle = "Animation sets, style, loop, interpolator and duration",
        options = listOf(
            FeatureOption("anim_expand_enable", "Enable animation set 1", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("anim_expand_style", "Animation style (set 1)", type = FeatureControlType.LIST, defaultValue = "5", choices = listOf("1", "2", "3", "4", "5")),
            FeatureOption("loopanim", "Loop mode (set 1)", type = FeatureControlType.LIST, defaultValue = "3", choices = listOf("1", "2", "3")),
            FeatureOption("anim_expand_interpolator", "Interpolator (set 1)", type = FeatureControlType.LIST, defaultValue = "5", choices = listOf("1", "2", "3", "4", "5")),
            FeatureOption("anim_expand_duration", "Duration (set 1)", type = FeatureControlType.SLIDER, defaultValue = "1000"),
            FeatureOption("anim_expand_enable2", "Enable animation set 2", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("anim_expand_style2", "Animation style (set 2)", type = FeatureControlType.LIST, defaultValue = "4", choices = listOf("1", "2", "3", "4", "5")),
            FeatureOption("loopanim2", "Loop mode (set 2)", type = FeatureControlType.LIST, defaultValue = "3", choices = listOf("1", "2", "3")),
            FeatureOption("anim_expand_interpolator2", "Interpolator (set 2)", type = FeatureControlType.LIST, defaultValue = "5", choices = listOf("1", "2", "3", "4", "5")),
            FeatureOption("anim_expand_duration2", "Duration (set 2)", type = FeatureControlType.SLIDER, defaultValue = "1000")
        )
    )

    val lockscreen = FeatureSection(
        title = "Lockscreen",
        subtitle = "Notifications and charging info behavior",
        options = listOf(
            FeatureOption("show_notif_mezo_lock", "Show notifications on lockscreen", type = FeatureControlType.TOGGLE, defaultValue = "true"),
            FeatureOption("vibrate_mezo_on", "Vibration", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("charge_animation_type", "Charging animation style", type = FeatureControlType.LIST, defaultValue = "1", choices = listOf("Wave", "Pulse", "Classic")),
            FeatureOption("bg_extra_charging_info", "Show charging info", type = FeatureControlType.TOGGLE, defaultValue = "true"),
            FeatureOption("bg_show_temp", "Show temperature", type = FeatureControlType.TOGGLE, defaultValue = "true"),
            FeatureOption("bg_show_ampere", "Show ampere", type = FeatureControlType.TOGGLE, defaultValue = "true"),
            FeatureOption("bg_show_voltage", "Show voltage", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("bg_show_power", "Show power", type = FeatureControlType.TOGGLE, defaultValue = "true"),
            FeatureOption("bg_refresh_interval", "Refresh interval", type = FeatureControlType.LIST, defaultValue = "2000", choices = listOf("1000", "2000", "3000", "5000")),
            FeatureOption("bg_charging_info_text_size", "Charging text size", type = FeatureControlType.SLIDER, defaultValue = "15")
        )
    )

    val controlCenter = FeatureSection(
        title = "Control Center / Quick Settings",
        subtitle = "Tile count, rows and color customization",
        options = listOf(
            FeatureOption("toggles_mezo_count_single", "Tile count (normal)", type = FeatureControlType.SLIDER, defaultValue = "6"),
            FeatureOption("toggles_mezo_count_expanded", "Tile count (expanded)", type = FeatureControlType.SLIDER, defaultValue = "6"),
            FeatureOption("toggles_mezo_count_expanded_rows", "Rows (expanded)", type = FeatureControlType.SLIDER, defaultValue = "3"),
            FeatureOption("QS_bg_color_show", "Enable custom tile colors", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("QS_bg_color_enabled", "Tile enabled background", type = FeatureControlType.COLOR, defaultValue = "#ff00ff00"),
            FeatureOption("QS_bg_color_disabled", "Tile disabled background", type = FeatureControlType.COLOR, defaultValue = "#1a000000"),
            FeatureOption("QS_bg_color", "Tile base background", type = FeatureControlType.COLOR, defaultValue = "#00000000"),
            FeatureOption("new_QS_background_color_force", "Force background color", type = FeatureControlType.TOGGLE, defaultValue = "false"),
            FeatureOption("new_QS_background_alpha", "Background alpha", type = FeatureControlType.SLIDER, defaultValue = "0"),
            FeatureOption("QS_fg_color_enabled", "Enabled foreground color", type = FeatureControlType.COLOR, defaultValue = "#ffffffff"),
            FeatureOption("QS_fg_color_disabled", "Disabled foreground color", type = FeatureControlType.COLOR, defaultValue = "#ffffffff"),
            FeatureOption("restart_systemui", "Restart SystemUI", summary = "Applies visual updates immediately", type = FeatureControlType.ACTION, defaultValue = "idle", requiresRootAction = true)
        )
    )
}
