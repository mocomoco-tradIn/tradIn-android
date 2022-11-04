package com.mocomoco.tradin.data.data.dto.request_body

data class AuthCoincideBody(
    val code: String,
    val region: String = "82",
    val tel: String
)