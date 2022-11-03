package com.mocomoco.tradin.data.data.resource.remote

import com.mocomoco.tradin.data.data.dto.request_body.*
import com.mocomoco.tradin.data.data.dto.response.NicknameDuplicateBody
import com.mocomoco.tradin.data.data.dto.response.PhoneAuthDto
import com.mocomoco.tradin.data.data.dto.response.login.SignInDto
import com.mocomoco.tradin.data.data.dto.response.refresh_token.RefreshTokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApi {
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

    @POST("/api/v1/auth/signin")
    suspend fun postSignIn(@Body body: SignInBody): Response<SignInDto>

    @POST("/api/v1/auth/token/refresh")
    suspend fun postRefreshAccessToken(@Body body: RefreshTokenBody): Response<RefreshTokenDto>
}
