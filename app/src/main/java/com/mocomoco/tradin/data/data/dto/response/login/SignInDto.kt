package com.mocomoco.tradin.data.data.dto.response.login

data class SignInDto(
    val accessToken: AccessToken,
    val info: Info,
    val refreshToken: RefreshToken
)