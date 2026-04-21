package com.deadzon.app.core.overlay

import javax.inject.Inject

class OverlayCommandBuilder @Inject constructor() {
    fun list(): String = "cmd overlay list --user 0"
    fun enable(pkg: String): String = "cmd overlay enable --user 0 $pkg"
    fun disable(pkg: String): String = "cmd overlay disable --user 0 $pkg"
    fun restartSystemUi(): String = "pkill -f com.android.systemui"
}
