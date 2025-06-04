package com.example.securenote.data.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.securenote.data.local.AppSettingDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class AppSettingDataSourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    AppSettingDataSource {

    private var _isDarkMode = MutableStateFlow(false)
    override val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()
    private val scope = CoroutineScope(SupervisorJob())

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
        private val FIRST_LAUNCH_KEY = booleanPreferencesKey("is_first_launch")
    }

    init {
        observeDataStore()
    }

    private fun observeDataStore() {
        scope.launch {
            dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[DARK_MODE_KEY] == true
                }
                .collect { isDark ->
                    _isDarkMode.value = isDark
                }
        }
    }

    override fun isFirstLaunch(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
            .map { preferences ->
                preferences[FIRST_LAUNCH_KEY] != false
            }
    }

    override suspend fun setFirstLaunchDone() {
        dataStore.edit { preferences ->
            preferences[FIRST_LAUNCH_KEY] = false
        }
    }


    override suspend fun switchUIMode(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = value
        }
    }
}