package com.mocomoco.tradin.data.data.resource.remote

import com.mocomoco.tradin.data.data.repository.SignupRepository
import com.mocomoco.tradin.data.data.resource.local.PreferenceService
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

//class InterceptorClient @Inject constructor(
//    private val preferenceService: PreferenceService,
//    private val signupRepository: SignupRepository
//) : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        return try {
//            val newRequest = chain.request().newBuilder()
//                .addHeader()
//        }
//    }
//}