package com.mocomoco.tradin.data.data.resource.remote

import com.mocomoco.tradin.data.common.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitService @Inject constructor(
    apiHeaderInterceptor: ApiHeaderInterceptor,
) {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_DEV)
            .client(OkHttpClient.Builder().addInterceptor(apiHeaderInterceptor).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}