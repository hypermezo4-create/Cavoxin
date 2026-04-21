package com.deadzon.app.data.overlay

import androidx.compose.ui.graphics.Color
import com.deadzon.app.domain.monet.MonetPreset
import com.deadzon.app.domain.monet.MonetTarget
import com.deadzon.app.domain.monet.TargetCategory

object MonetOverlayRegistry {
    val presets = listOf(
        MonetPreset("stock", "Stock Monet", Color(0xFF7C4DFF), listOf(Color(0xFF7C4DFF), Color(0xFFB388FF), Color(0xFFEDE7F6))),
        MonetPreset("crimson", "Crimson", Color(0xFFD32F2F), listOf(Color(0xFFD32F2F), Color(0xFFFF5252), Color(0xFFFFCDD2))),
        MonetPreset("gold", "Gold", Color(0xFFFFB300), listOf(Color(0xFFFFB300), Color(0xFFFFD54F), Color(0xFFFFF8E1))),
        MonetPreset("blue", "Blue", Color(0xFF1976D2), listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color(0xFFE3F2FD))),
        MonetPreset("purple", "Purple", Color(0xFF6A1B9A), listOf(Color(0xFF6A1B9A), Color(0xFFBA68C8), Color(0xFFF3E5F5))),
        MonetPreset("emerald", "Emerald", Color(0xFF00796B), listOf(Color(0xFF00796B), Color(0xFF4DB6AC), Color(0xFFE0F2F1))),
        MonetPreset("graphite", "Graphite", Color(0xFF455A64), listOf(Color(0xFF455A64), Color(0xFF90A4AE), Color(0xFFECEFF1))),
        MonetPreset("amoled", "AMOLED Mono", Color(0xFFB71C1C), listOf(Color(0xFF000000), Color(0xFF1A1A1A), Color(0xFFB71C1C)))
    )

    val overlayMap = linkedMapOf(
        "paperos_monet_androidbluetooth" to "com.paperos.monet.androidbluetooth",
        "paperos_monet_aod" to "com.paperos.monet.aod",
        "paperos_monet_backup" to "com.paperos.monet.backup",
        "paperos_monet_bluetooth" to "com.paperos.monet.bluetooth",
        "paperos_monet_bugreport" to "com.paperos.monet.bugreport",
        "paperos_monet_calendar" to "com.paperos.monet.calendar",
        "paperos_monet_cloudservice" to "com.paperos.monet.cloudservice",
        "paperos_monet_camera" to "com.paperos.monet.camera",
        "paperos_monet_cleanmaster" to "com.paperos.monet.cleanmaster",
        "paperos_monet_cloudbackup" to "com.paperos.monet.cloudbackup",
        "paperos_monet_contacts" to "com.paperos.monet.contacts",
        "paperos_monet_calculator" to "com.paperos.monet.calculator",
        "paperos_monet_deskclock" to "com.paperos.monet.deskclock",
        "paperos_monet_downloads" to "com.paperos.monet.downloads",
        "paperos_monet_extraphoto" to "com.paperos.monet.extraphoto",
        "paperos_monet_finddevice" to "com.paperos.monet.finddevice",
        "paperos_monet_fileexplorer" to "com.paperos.monet.fileexplorer",
        "paperos_monet_freeform" to "com.paperos.monet.freeform",
        "paperos_monet_gallery" to "com.paperos.monet.gallery",
        "paperos_monet_home" to "com.paperos.monet.home",
        "paperos_monet_incallui" to "com.paperos.monet.incallui",
        "paperos_monet_miinput" to "com.paperos.monet.miinput",
        "paperos_monet_misound" to "com.paperos.monet.misound",
        "paperos_monet_milink_service" to "com.paperos.monet.milink_service",
        "paperos_monet_mirror" to "com.paperos.monet.mirror",
        "paperos_monet_mishare" to "com.paperos.monet.mishare",
        "paperos_monet_mediaeditor" to "com.paperos.monet.mediaeditor",
        "paperos_monet_miservice" to "com.paperos.monet.miservice",
        "paperos_monet_misettings" to "com.paperos.monet.misettings",
        "paperos_monet_mms" to "com.paperos.monet.mms",
        "paperos_monet_notes" to "com.paperos.monet.notes",
        "paperos_monet_notification" to "com.paperos.monet.notification",
        "paperos_monet_personalassistant" to "com.paperos.monet.personalassistant",
        "paperos_monet_packageinstaller" to "com.paperos.monet.packageinstaller",
        "paperos_monet_powerkeeper" to "com.paperos.monet.powerkeeper",
        "paperos_monet_permission" to "com.paperos.monet.permission",
        "paperos_monet_phone" to "com.paperos.monet.phone",
        "paperos_monet_settings" to "com.paperos.monet.settings",
        "paperos_monet_screenrecorder" to "com.paperos.monet.screenrecorder",
        "paperos_monet_server_telecom" to "com.paperos.monet.server_telecom",
        "paperos_monet_screenshot" to "com.paperos.monet.screenshot",
        "paperos_monet_securitycenter" to "com.paperos.monet.securitycenter",
        "paperos_monet_scanner" to "com.paperos.monet.scanner",
        "paperos_monet_securitycore" to "com.paperos.monet.securitycore",
        "paperos_monet_systemui" to "com.paperos.monet.systemui",
        "paperos_monet_systemuiplugin" to "com.paperos.monet.systemuiplugin",
        "paperos_monet_soundrecorder" to "com.paperos.monet.soundrecorder",
        "paperos_monet_touchassistant" to "com.paperos.monet.touchassistant",
        "paperos_monet_thememanager" to "com.paperos.monet.thememanager",
        "paperos_monet_updater" to "com.paperos.monet.updater",
        "paperos_monet_weather2" to "com.paperos.monet.weather2",
        "paperos_monet_xiaomiaccount" to "com.paperos.monet.xiaomiaccount"
    )

    val targets = overlayMap.map { (key, pkg) ->
        MonetTarget(
            preferenceKey = key,
            title = key.removePrefix("paperos_monet_").replace('_', ' ').replaceFirstChar { it.uppercase() },
            overlayPackage = pkg,
            category = inferCategory(key)
        )
    }

    private fun inferCategory(key: String): TargetCategory = when {
        key.contains("system") || key.contains("home") || key.contains("settings") -> TargetCategory.CORE_SYSTEM
        key.contains("camera") || key.contains("gallery") || key.contains("media") || key.contains("screenrecorder") || key.contains("soundrecorder") -> TargetCategory.MEDIA
        key.contains("mi") || key.contains("xiaomi") || key.contains("security") -> TargetCategory.MIUI
        else -> TargetCategory.UTILITIES
    }
}
