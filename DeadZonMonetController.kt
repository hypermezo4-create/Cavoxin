package com.deadzon.monet

import android.content.ComponentName
import android.content.Context
import android.content.Intent

/**
 * Codex starter skeleton for the Dead Zon Monet module.
 * Replace TODOs with the privilege model you choose:
 * - system/priv-app direct IOverlayManager
 * - root shell wrapper
 * - Shizuku bridge
 */
object MonetOverlayMap {
    val entries: Map<String, String> = mapOf(
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
}

interface OverlayController {
    fun setOverlayEnabled(packageName: String, enabled: Boolean): Result<Unit>
    fun setManyEnabled(packageNames: List<String>, enabled: Boolean): Result<Unit>
}

class MonetModule(private val overlayController: OverlayController) {

    fun applyGlobal(enabled: Boolean, selectedKeys: Set<String>): Result<Unit> {
        val targets = MonetOverlayMap.entries
            .filterKeys { it in selectedKeys }
            .values
            .toList()
        return overlayController.setManyEnabled(targets, enabled)
    }

    fun applySingle(preferenceKey: String, enabled: Boolean): Result<Unit> {
        val target = MonetOverlayMap.entries[preferenceKey]
            ?: return Result.failure(IllegalArgumentException("Unknown preference key: $preferenceKey"))
        return overlayController.setOverlayEnabled(target, enabled)
    }

    fun launchMonetPicker(context: Context) {
        val intent = Intent().apply {
            component = ComponentName(
                "com.android.wallpaper",
                "com.android.wallpaper.picker.CustomizationPickerActivity"
            )
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}

/**
 * Optional convenience grouping for a cleaner Dead Zon UI.
 */
object MonetGroups {
    val core = listOf(
        "paperos_monet_settings",
        "paperos_monet_systemui",
        "paperos_monet_systemuiplugin",
        "paperos_monet_home",
        "paperos_monet_thememanager"
    )

    val media = listOf(
        "paperos_monet_gallery",
        "paperos_monet_extraphoto",
        "paperos_monet_mediaeditor",
        "paperos_monet_camera",
        "paperos_monet_screenrecorder",
        "paperos_monet_soundrecorder",
        "paperos_monet_scanner"
    )

    val communication = listOf(
        "paperos_monet_contacts",
        "paperos_monet_incallui",
        "paperos_monet_phone",
        "paperos_monet_mms",
        "paperos_monet_androidbluetooth",
        "paperos_monet_bluetooth"
    )

    val tools = listOf(
        "paperos_monet_fileexplorer",
        "paperos_monet_calculator",
        "paperos_monet_calendar",
        "paperos_monet_notes",
        "paperos_monet_downloads",
        "paperos_monet_updater",
        "paperos_monet_backup"
    )
}
