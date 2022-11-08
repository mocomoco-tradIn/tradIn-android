package com.mocomoco.tradin.data.data.resource.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceService @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun isLogin(): Boolean {
        return getAccessToken() != null
    }



    fun setAccessToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN, null)
    }

    fun setAccessTokenKey(key: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, key).apply()
    }

    fun getAccessTokenKey(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    fun setRefreshToken(token: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN, token).apply()
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN, null)
    }

    fun setRefreshTokenKey(key: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN_KEY, key).apply()
    }

    fun getRefreshTokenKey(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

    fun setLocation(location: String) {
        sharedPreferences.edit().putString(LAST_LOCATION, location).apply()
    }

    fun getLocation(): String? {
        return sharedPreferences.getString(LAST_LOCATION, null)
    }

    companion object {
        const val PREFERENCE_NAME = "tradIn"

        const val ACCESS_TOKEN = "accessToken"
        const val ACCESS_TOKEN_KEY = "accessTokenKey"
        const val REFRESH_TOKEN = "refreshToken"
        const val REFRESH_TOKEN_KEY = "refreshTokenKey"

        const val LAST_LOCATION = "lastLocation"

        const val AUTO_LOGIN = "autoLogin"
    }
}