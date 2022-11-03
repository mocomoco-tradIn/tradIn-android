package com.mocomoco.tradin.data.data.resource.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceService @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setAutoLogin(enable: Boolean) {
        sharedPreferences.edit().putBoolean(AUTO_LOGIN, enable).apply()
    }

    companion object {
        const val PREFERENCE_NAME = "tradIn"
        const val AUTO_LOGIN = "autoLogin"
    }
}