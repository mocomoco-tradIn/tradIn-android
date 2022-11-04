package com.mocomoco.tradin.data.data.dto.request_body

data class SignInBody(
    val devicePlatform: String,
    val deviceToken: String,
    val id: String,
    val password: String
)