package com.deadzon.app.domain.monet

import com.deadzon.app.data.overlay.MonetOverlayRegistry
import com.deadzon.app.data.overlay.OverlayRepository
import javax.inject.Inject

class MonetController @Inject constructor(
    private val overlayRepository: OverlayRepository
) {
    suspend fun apply(selectedKeys: Set<String>, monetEnabled: Boolean, restartSystemUi: Boolean): ApplyResult {
        val selectedPackages = MonetOverlayRegistry.targets
            .filter { it.preferenceKey in selectedKeys }
            .map { it.overlayPackage }
            .toSet()
        return overlayRepository.apply(selectedPackages, monetEnabled, restartSystemUi)
    }

    suspend fun reset(): ApplyResult = overlayRepository.apply(emptySet(), monetEnabled = false, restartSystemUi = false)

    suspend fun hasRoot(): Boolean = overlayRepository.checkRoot()
}
