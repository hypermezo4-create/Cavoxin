package com.deadzon.app.data.overlay

import com.deadzon.app.core.overlay.OverlayCommandBuilder
import com.deadzon.app.core.root.RootShell
import com.deadzon.app.domain.monet.ApplyResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OverlayRepository @Inject constructor(
    private val rootShell: RootShell,
    private val cmd: OverlayCommandBuilder
) {
    suspend fun checkRoot(): Boolean = rootShell.isRootAvailable()

    suspend fun apply(selectedPackages: Set<String>, monetEnabled: Boolean, restartSystemUi: Boolean): ApplyResult {
        val logs = mutableListOf<String>()
        if (!rootShell.isRootAvailable()) {
            return ApplyResult(false, emptyList(), emptyList(), selectedPackages.toList(), listOf("Root is unavailable."))
        }

        val disableResults = mutableListOf<String>()
        val enableResults = mutableListOf<String>()
        val missing = mutableListOf<String>()

        val installedOutput = rootShell.run(cmd.list()).stdout

        MonetOverlayRegistry.overlayMap.values.forEach { pkg ->
            val shouldEnable = monetEnabled && pkg in selectedPackages
            val exists = installedOutput.contains(pkg)
            if (!exists) {
                missing += pkg
                logs += "Missing overlay: $pkg"
                return@forEach
            }
            val result = if (shouldEnable) rootShell.run(cmd.enable(pkg)) else rootShell.run(cmd.disable(pkg))
            if (result.exitCode == 0) {
                if (shouldEnable) enableResults += pkg else disableResults += pkg
                logs += "OK ${if (shouldEnable) "enable" else "disable"}: $pkg"
            } else {
                logs += "FAIL ${if (shouldEnable) "enable" else "disable"}: $pkg ${result.stderr}"
            }
        }

        if (restartSystemUi) {
            val restartResult = rootShell.run(cmd.restartSystemUi())
            logs += if (restartResult.exitCode == 0) "SystemUI restart requested." else "SystemUI restart failed: ${restartResult.stderr}"
        }

        return ApplyResult(true, enableResults, disableResults, missing, logs)
    }
}
