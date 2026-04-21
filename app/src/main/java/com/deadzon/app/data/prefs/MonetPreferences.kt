package com.deadzon.app.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.deadzon.app.data.overlay.MonetOverlayRegistry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.monetDataStore by preferencesDataStore(name = "deadzon_prefs")

@Singleton
class MonetPreferences @Inject constructor(
    private val context: Context
) {
    private object Keys {
        val monetEnabled = booleanPreferencesKey("monet_enabled")
        val applyToApp = booleanPreferencesKey("apply_to_app")
        val preset = stringPreferencesKey("preset")
        val targets = stringPreferencesKey("targets")
    }

    val state: Flow<StoredMonetState> = context.monetDataStore.data.map { prefs ->
        StoredMonetState(
            monetEnabled = prefs[Keys.monetEnabled] ?: true,
            applyToApp = prefs[Keys.applyToApp] ?: true,
            presetId = prefs[Keys.preset] ?: "stock",
            selectedTargets = (prefs[Keys.targets]?.split(',')?.filter { it.isNotBlank() }
                ?.toSet() ?: MonetOverlayRegistry.targets.map { it.preferenceKey }.toSet())
        )
    }

    suspend fun update(transform: (StoredMonetState) -> StoredMonetState) {
        context.monetDataStore.edit { prefs: MutablePreferences ->
            val current = StoredMonetState(
                monetEnabled = prefs[Keys.monetEnabled] ?: true,
                applyToApp = prefs[Keys.applyToApp] ?: true,
                presetId = prefs[Keys.preset] ?: "stock",
                selectedTargets = (prefs[Keys.targets]?.split(',')?.filter { it.isNotBlank() }
                    ?.toSet() ?: MonetOverlayRegistry.targets.map { it.preferenceKey }.toSet())
            )
            val next = transform(current)
            prefs[Keys.monetEnabled] = next.monetEnabled
            prefs[Keys.applyToApp] = next.applyToApp
            prefs[Keys.preset] = next.presetId
            prefs[Keys.targets] = next.selectedTargets.joinToString(",")
        }
    }
}

data class StoredMonetState(
    val monetEnabled: Boolean,
    val applyToApp: Boolean,
    val presetId: String,
    val selectedTargets: Set<String>
)

typealias MutablePreferences = androidx.datastore.preferences.core.MutablePreferences
