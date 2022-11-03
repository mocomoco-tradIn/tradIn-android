package com.mocomoco.tradin.data.data.dto.response.login

data class RefreshToken(
    val expiredAt: Int,
    val key: String,
    val value: String
)