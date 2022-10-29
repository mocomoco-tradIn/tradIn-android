package com.mocomoco.tradin.data.data.resource.remote

import com.mocomoco.tradin.data.data.dto.request_body.PhoneAuthBody
import com.mocomoco.tradin.data.data.dto.response.PhoneAuthDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupApi {
    @POST("/api/v1/auth/verification/sms")
    suspend fun requestPhoneAuth(@Body phoneAuthBody: PhoneAuthBody): Response<PhoneAuthDto>
}