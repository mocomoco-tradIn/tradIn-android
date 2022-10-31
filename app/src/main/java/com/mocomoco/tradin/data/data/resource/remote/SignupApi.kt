package com.mocomoco.tradin.data.data.resource.remote

import com.mocomoco.tradin.data.data.dto.request_body.EmailDuplicateBody
import com.mocomoco.tradin.data.data.dto.request_body.TelBody
import com.mocomoco.tradin.data.data.dto.request_body.AuthCoincideBody
import com.mocomoco.tradin.data.data.dto.request_body.SignupBody
import com.mocomoco.tradin.data.data.dto.response.EmailDuplicateDto
import com.mocomoco.tradin.data.data.dto.response.NicknameDuplicateBody
import com.mocomoco.tradin.data.data.dto.response.PhoneAuthDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface SignupApi {
    @POST("/api/v1/auth/verification/sms")
    suspend fun postTelAuth(@Body body: TelBody): Response<PhoneAuthDto>

    @PUT("/api/v1/auth/verification/sms")
    suspend fun putAuthCoincide(@Body body: AuthCoincideBody): Response<Unit>

    @POST("/api/v1/auth/verification/email")
    suspend fun postEmailDuplicate(@Body body: EmailDuplicateBody): Response<Unit>

    @POST("/api/v1/auth/verification/nickname")
    suspend fun postNickDuplicate(@Body body: NicknameDuplicateBody): Response<Unit>

    @POST("/api/v1/auth/signup")
    suspend fun postSignup(@Body body: SignupBody): Response<Unit>
}
