package com.mocomoco.tradin.data.data.repository

import com.mocomoco.tradin.data.data.dto.request_body.RefreshTokenBody
import com.mocomoco.tradin.data.data.dto.response.refresh_token.RefreshTokenDto

interface RefreshTokenRepository {
    suspend fun postRefreshToken(body: RefreshTokenBody): RefreshTokenDto
}