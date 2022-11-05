package com.mocomoco.tradin.data.data.resource.remote

import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.data.data.dto.request_body.RefreshTokenBody
import com.mocomoco.tradin.data.data.resource.local.PreferenceService
import com.mocomoco.tradin.data.data.resource.remote.apis.RefreshTokenApi
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHeaderInterceptor @Inject constructor(
    private val preferenceService: PreferenceService,
    private val refreshTokenApi: RefreshTokenApi
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Logger.log("intercept")

        val token = preferenceService.getAccessToken()
        val key = preferenceService.getAccessTokenKey()
        val request = if (token != null && key != null) {
            Logger.log("key $key / token $token ")

            chain.request().newBuilder()
                .addHeader(KEY, key)
                .addHeader(AUTH, "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        val response = chain.proceed(request)
        Logger.logE("response ${response}")
        Logger.logE("response \n isSuccessful ${response.isSuccessful} / code ${response.code} / message ${response.message}")
        if (!response.isSuccessful && response.code == 401) {
            val body = response.body?.string() ?: ""
            Logger.logE("body $body")
            if (body.contains("C006")) {
                val refreshToken = preferenceService.getRefreshToken()
                val key = preferenceService.getRefreshTokenKey()
                if (refreshToken != null && key != null) {
                    runBlocking {
                        try {
                            val body = refreshTokenApi.postRefreshAccessToken(
                                RefreshTokenBody(
                                    token = refreshToken,
                                    key = key
                                )
                            ).body()
                            body?.token?.let {
                                preferenceService.setAccessTokenKey(it.key)
                                preferenceService.setAccessToken(it.value)
                            }
                        } catch (e: Exception) {
                            // todo
                        }
                    }
                }
            }
        }

        return response
    }


    companion object {
        const val KEY = "Kid"
        const val AUTH = "Authorization"
    }
}