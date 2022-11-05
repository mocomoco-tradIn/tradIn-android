package com.mocomoco.tradin.data.data.resource.remote.apis

import com.mocomoco.tradin.data.data.dto.request_body.RefreshTokenBody
import com.mocomoco.tradin.data.data.dto.response.refresh_token.RefreshTokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenApi {
    @POST("/api/v1/auth/token/refresh")
    suspend fun postRefreshAccessToken(@Body body: RefreshTokenBody): Response<RefreshTokenDto>
}