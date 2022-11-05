package com.mocomoco.tradin.data.data.repository.impl

import com.mocomoco.tradin.data.common.handleResponse
import com.mocomoco.tradin.data.data.dto.request_body.RefreshTokenBody
import com.mocomoco.tradin.data.data.dto.response.refresh_token.RefreshTokenDto
import com.mocomoco.tradin.data.data.repository.RefreshTokenRepository
import com.mocomoco.tradin.data.data.resource.remote.apis.RefreshTokenApi
import javax.inject.Inject

class RefreshTokenRepositoryImpl @Inject constructor(
    private val refreshTokenApi: RefreshTokenApi
): RefreshTokenRepository {
    override suspend fun postRefreshToken(body: RefreshTokenBody): RefreshTokenDto {
        return handleResponse(refreshTokenApi.postRefreshAccessToken(body))
    }
}

