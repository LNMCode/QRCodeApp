package com.ricker.qrcodeapp.datastore

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import com.ricker.qrcodeapp.presentation.BaseApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingsDataStore
@Inject
constructor(app: BaseApplication){
    private val dataStore: DataStore<Preferences> = app.createDataStore(name = "settings")

    private val scope = CoroutineScope(Main)

    init {
        observeDataStore()
    }

    val isDark = mutableStateOf(false)

    fun toggleTheme(){
        scope.launch {
            dataStore.edit { prefercences ->
                val current = prefercences[DARK_THEME_KEY]?: false
                prefercences[DARK_THEME_KEY] = !current
            }
        }
    }

    private fun observeDataStore(){
        dataStore.data.onEach { preferences ->
            preferences[DARK_THEME_KEY]?.let { isDarkTheme ->
                isDark.value = isDarkTheme
            }
        }.launchIn(scope)
    }

    companion object{
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_key")
    }
}