package com.patan.data.auth.ravelry

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.preferencesDataStore

private val Context.ds by preferencesDataStore("auth_tmp")
private val KEY_STATE = stringPreferencesKey("state")

@Singleton
class LoginTempStore @Inject constructor(
    @ApplicationContext private val ctx: Context
) {
    suspend fun saveState(s: String) = ctx.ds.edit { it[KEY_STATE] = s }
    suspend fun readState(): String? = ctx.ds.data.first()[KEY_STATE]
    suspend fun clear() = ctx.ds.edit { it.clear() }
}