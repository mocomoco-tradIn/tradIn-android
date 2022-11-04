package com.mocomoco.tradin.data.data.dto.request_body

data class SignupBody(
    val category: List<Int>,
    val email: String,
    val nickname: String,
    val password: String,
    val regionCode: String,
    val tel: String
)