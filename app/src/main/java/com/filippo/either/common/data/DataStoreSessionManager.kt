package com.filippo.either.common.data

import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import com.filippo.either.common.domain.SessionProvider
import com.filippo.either.common.domain.SessionWriter
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

@Singleton
class DataStoreSessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SessionWriter,
    SessionProvider {

    override val sessionId: Flow<String?> = dataStore.data
        .map { preferences -> preferences[SESSION_KEY] }

    override suspend fun saveSessionId(sessionId: String) {
        dataStore.edit { preferences ->
            preferences[SESSION_KEY] = sessionId
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(SESSION_KEY)
        }
    }

    companion object {
        private val SESSION_KEY = stringPreferencesKey("session_id")
    }
}
