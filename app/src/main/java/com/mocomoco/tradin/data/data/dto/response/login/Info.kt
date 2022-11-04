package com.mocomoco.tradin.data.data.dto.response.login

data class Info(
    val avatar: String,
    val category: List<String>,
    val email: String,
    val nickname: String,
    val regionCode: String,
    val regionName: String,
    val tel: String,
    val userId: Int
)