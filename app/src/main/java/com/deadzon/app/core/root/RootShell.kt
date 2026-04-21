package com.deadzon.app.core.root

import com.deadzon.app.domain.monet.RootCommandResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

interface RootShell {
    suspend fun isRootAvailable(): Boolean
    suspend fun run(command: String): RootCommandResult
}

@Singleton
class SuRootShell @Inject constructor() : RootShell {
    override suspend fun isRootAvailable(): Boolean = withContext(Dispatchers.IO) {
        try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "id"))
            val stdout = process.inputStream.bufferedReader().readText()
            process.waitFor() == 0 && stdout.contains("uid=0")
        } catch (_: Exception) {
            false
        }
    }

    override suspend fun run(command: String): RootCommandResult = withContext(Dispatchers.IO) {
        try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", command))
            val stdout = BufferedReader(InputStreamReader(process.inputStream)).readText()
            val stderr = BufferedReader(InputStreamReader(process.errorStream)).readText()
            val code = process.waitFor()
            RootCommandResult(command, code, stdout, stderr)
        } catch (e: Exception) {
            RootCommandResult(command, -1, "", e.message.orEmpty())
        }
    }
}
