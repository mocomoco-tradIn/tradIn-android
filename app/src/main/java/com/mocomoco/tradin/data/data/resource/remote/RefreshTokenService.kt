package com.mocomoco.tradin.data.data.resource.remote

import com.mocomoco.tradin.data.common.Constants
import com.mocomoco.tradin.data.data.resource.remote.ApiHeaderInterceptor
import com.mocomoco.tradin.data.data.resource.remote.apis.RefreshTokenApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshTokenService @Inject constructor() {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_DEV)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}